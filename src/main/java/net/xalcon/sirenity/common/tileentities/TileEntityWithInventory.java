package net.xalcon.sirenity.common.tileentities;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextComponentTranslation;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;

public abstract class TileEntityWithInventory extends TileEntitySirenityBase implements ISidedInventory
{
	private static final int[] NO_SLOTS = new int[0];
	protected ArrayList<ItemStack> inventory = new ArrayList<>();

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
	@Nonnull
	public ItemStack getStackInSlot(int index)
	{
		return this.inventory.get(index);
	}

	@Override
	@Nonnull
	public ItemStack decrStackSize(int index, int count)
	{
		return this.inventory.get(index);
	}

	@Override
	@Nonnull
	public ItemStack removeStackFromSlot(int index)
	{
		return this.inventory.get(index);
	}

	@Override
	public void setInventorySlotContents(int index, @Nonnull ItemStack stack)
	{
	}

	@Override
	public int getInventoryStackLimit()
	{
		return 64;
	}

	@Override
	public boolean isUsableByPlayer(@Nonnull EntityPlayer player)
	{
		return true;
	}

	@Override
	public void openInventory(@Nonnull EntityPlayer player) { }

	@Override
	public void closeInventory(@Nonnull EntityPlayer player) { }

	@Override
	public boolean isItemValidForSlot(int index, @Nonnull ItemStack stack)
	{
		return false;
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
	public void clear() { }

	@Override
	@Nonnull
	public abstract String getName();

	@Override
	public boolean hasCustomName()
	{
		return false;
	}


	@Nonnull
	@Override
	public ITextComponent getDisplayName()
	{
		return this.hasCustomName() ? new TextComponentString(this.getName()) : new TextComponentTranslation(this.getName());
	}

	@Override
	@Nonnull
	public int[] getSlotsForFace(@Nonnull EnumFacing side)
	{
		return NO_SLOTS;
	}

	@Override
	public boolean canInsertItem(int index, @Nonnull ItemStack itemStackIn, @Nonnull EnumFacing direction)
	{
		return false;
	}

	@Override
	public boolean canExtractItem(int index, @Nonnull ItemStack stack, @Nonnull EnumFacing direction)
	{
		return false;
	}
}
