package net.xalcon.minefactory.common.tileentities;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.inventory.InventoryHelper;
import net.minecraft.inventory.ItemStackHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.NonNullList;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextComponentTranslation;

import javax.annotation.Nonnull;

public abstract class TileEntityWithInventory extends TileEntityMFBase implements ISidedInventory
{
	protected NonNullList<ItemStack> inventory = NonNullList.create();

	// NBT Overrides
	@Override
	public void readFromNBT(NBTTagCompound compound)
	{
		super.readFromNBT(compound);
		ItemStackHelper.loadAllItems(compound, this.inventory);
	}

	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound compound)
	{
		ItemStackHelper.saveAllItems(compound, this.inventory);
		return super.writeToNBT(compound);
	}

	public void insertItemStack(ItemStack itemStack)
	{
		for(int i = 1; !itemStack.isEmpty() && i < inventory.size(); i++)
		{
			ItemStack inventorySlot = inventory.get(i);
			if(!inventorySlot.isEmpty())
			{
				if(inventorySlot.getItem() != itemStack.getItem()) continue;
				if(!inventorySlot.isStackable()) continue;
				if(inventorySlot.getMetadata() != itemStack.getMetadata()) continue;
				if(inventorySlot.getMaxStackSize() < inventorySlot.getCount()) continue;
				int spaceDelta = inventorySlot.getMaxStackSize() - inventorySlot.getCount();
				int delta = Math.min(itemStack.getCount(), spaceDelta);
				itemStack.shrink(delta);
				inventorySlot.grow(delta);
				this.markDirty();
			}
			else
			{
				inventory.set(i, itemStack.copy());
				itemStack = ItemStack.EMPTY;
				this.markDirty();
			}
		}
	}

	// IInventory implementation
	@Override
	public int getSizeInventory()
	{
		return inventory.size();
	}

	@Override
	public boolean isEmpty()
	{
		return inventory.stream().allMatch(ItemStack::isEmpty);
	}

	@Override
	public ItemStack getStackInSlot(int index)
	{
		return this.inventory.get(index);
	}

	@Override
	public ItemStack decrStackSize(int index, int count)
	{
		ItemStack itemstack = ItemStackHelper.getAndSplit(this.inventory, index, count);

		if (!itemstack.isEmpty())
			this.markDirty();

		return itemstack;
	}

	@Override
	public ItemStack removeStackFromSlot(int index)
	{
		return ItemStackHelper.getAndRemove(this.inventory, index);
	}

	@Override
	public void setInventorySlotContents(int index, @Nonnull ItemStack stack)
	{
		this.inventory.set(index, stack);
		if (stack.getCount() > this.getInventoryStackLimit())
			stack.setCount(this.getInventoryStackLimit());
		this.markDirty();
	}

	@Override
	public int getInventoryStackLimit()
	{
		return 64;
	}

	@Override
	public boolean isUsableByPlayer(@Nonnull EntityPlayer player)
	{
		return player.getDistanceSq((double)this.pos.getX() + 0.5D, (double)this.pos.getY() + 0.5D, (double)this.pos.getZ() + 0.5D) <= 64.0D;
	}

	@Override
	public void openInventory(@Nonnull EntityPlayer player) { }

	@Override
	public void closeInventory(@Nonnull EntityPlayer player) { }

	@Override
	public boolean isItemValidForSlot(int index, @Nonnull ItemStack stack)
	{
		return true;
	}

	@Override
	public int getField(int id)
	{
		return 0;
	}

	@Override
	public void setField(int id, int value) { }

	@Override
	public int getFieldCount()
	{
		return 0;
	}

	@Override
	public void clear()
	{
		this.inventory.clear();
	}

	@Override
	public abstract String getName();

	@Override
	public boolean hasCustomName()
	{
		return false;
	}

	@Override
	public ITextComponent getDisplayName()
	{
		return this.hasCustomName() ? new TextComponentString(this.getName()) : new TextComponentTranslation(this.getName());
	}
}
