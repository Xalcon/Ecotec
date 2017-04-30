package net.xalcon.ecotec.common.inventories.itemstackhandler;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.IItemHandlerModifiable;
import net.xalcon.ecotec.common.tileentities.NbtSyncType;
import net.xalcon.ecotec.common.tileentities.logistics.TileEntityDeepStorageUnit;
import net.xalcon.ecotec.core.IEcotecComponent;

import javax.annotation.Nonnull;

public class ItemStackHandlerDSU implements IItemHandler, IItemHandlerModifiable, IEcotecComponent
{
	private final static int SLOT_INDEX_MAIN = 0;
	private final static int SLOT_INDEX_VIRTUAL_INPUT = 1;
	private final static int SLOT_INDEX_VIRTUAL_OUTPUT_GUI_ONLY = 2;

	private ItemStack storedItem = ItemStack.EMPTY;
	private int count;
	private TileEntityDeepStorageUnit tile;

	public void setTile(TileEntityDeepStorageUnit tile)
	{
		this.tile = tile;
	}

	//region IItemHandler & IItemHandlerModifiable implementation
	@Override
	public int getSlots()
	{
		return 2;
	}

	@Override
	public ItemStack getStackInSlot(int slot)
	{
		if(slot == SLOT_INDEX_MAIN)
		{
			ItemStack outStack = this.storedItem.copy();
			outStack.setCount(this.count);
			return outStack;
		}
		else if(slot == SLOT_INDEX_VIRTUAL_OUTPUT_GUI_ONLY)
		{
			ItemStack outStack = this.storedItem.copy();
			outStack.setCount(Math.min(this.count, outStack.getMaxStackSize()));
			return outStack;
		}
		return ItemStack.EMPTY;
	}

	@Override
	public ItemStack insertItem(int slot, ItemStack stack, boolean simulate)
	{
		if(this.storedItem.isEmpty())
		{
			if(!simulate)
			{
				this.storedItem = stack.copy();
				this.count = stack.getCount();
				stack.setCount(0);
				this.onContentsChanged();
			}
			return ItemStack.EMPTY;
		}
		else if(this.storedItem.isItemEqual(stack))
		{
			if(!simulate)
			{
				this.count += stack.getCount();
				stack.setCount(0);
				this.onContentsChanged();
			}
			return ItemStack.EMPTY;
		}
		return stack;
	}


	@Override
	public ItemStack extractItem(int slot, int amount, boolean simulate)
	{
		if(this.storedItem.isEmpty()) return ItemStack.EMPTY;

		ItemStack itemStack = this.storedItem.copy();
		itemStack.setCount(Math.min(this.count, amount));
		if(!simulate)
		{
			this.count -= itemStack.getCount();
			this.onContentsChanged();
		}

		this.ensureEmpty();

		return itemStack;
	}

	private void ensureEmpty()
	{
		if(this.count == 0)
		{
			this.storedItem = ItemStack.EMPTY;
		}
	}

	@Override
	public int getSlotLimit(int slot)
	{
		return slot == SLOT_INDEX_MAIN ? Integer.MAX_VALUE : this.storedItem.getMaxStackSize();
	}

	@Override
	public void setStackInSlot(int slot, @Nonnull ItemStack stack)
	{
		if(slot == SLOT_INDEX_MAIN) return;

		if(slot == SLOT_INDEX_VIRTUAL_INPUT && !stack.isEmpty())
		{
			if(this.storedItem.isEmpty())
			{
				this.storedItem = stack.copy();
				this.count = stack.getCount();
				//stack.setCount(0);
				this.onContentsChanged();
			}
			else if(this.storedItem.isItemEqual(stack))
			{
				this.count += stack.getCount();
				//stack.setCount(0);
				this.onContentsChanged();
			}
		}
		else if(slot == SLOT_INDEX_VIRTUAL_OUTPUT_GUI_ONLY)
		{
			if(!stack.isEmpty()) return;
			ItemStack outStack = this.storedItem.copy();
			outStack.setCount(Math.min(this.count, outStack.getMaxStackSize()));
			this.count -= outStack.getCount();
		}
	}

	private void onContentsChanged()
	{
		this.tile.sendUpdate(false);
	}
	//endregion

	//region IEcotecComponent implementation
	@Override
	public void readSyncNbt(@Nonnull NBTTagCompound nbt, @Nonnull NbtSyncType type)
	{
		NBTTagCompound itemsNbt = nbt.getCompoundTag("Items");
		NBTTagCompound itemStackTags = itemsNbt.getCompoundTag("Item");
		this.storedItem = new ItemStack(itemStackTags);
		this.count = itemsNbt.getInteger("ItemCount");
		this.ensureEmpty();
	}

	@Override
	public void writeSyncNbt(@Nonnull NBTTagCompound nbt, @Nonnull NbtSyncType type)
	{
		NBTTagCompound itemsNbt = new NBTTagCompound();
		nbt.setTag("Items", itemsNbt);
		itemsNbt.setTag("Item", this.storedItem.writeToNBT(new NBTTagCompound()));
		itemsNbt.setInteger("ItemCount", this.count);
	}
	//endregion
}
