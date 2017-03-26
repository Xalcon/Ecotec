package net.xalcon.minefactory.common.tileentities;

import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;

import javax.annotation.Nonnull;

public abstract class TileEntityMachineBase extends TileEntityWithInventory
{
	private int[] SLOTS;

	public TileEntityMachineBase(int inventorySize)
	{
		this.inventory.add(ItemStack.EMPTY);
		SLOTS = new int[inventorySize];
		for(int i = 0; i < inventorySize; i++)
		{
			this.inventory.add(ItemStack.EMPTY);
			SLOTS[i] = i + 1;
		}
	}

	public int getUpgradeSlotIndex()
	{
		return 0;
	}

	public ItemStack getUpgradeSlotItemStack()
	{
		return this.inventory.get(this.getUpgradeSlotIndex());
	}

	// ISidedInventory implementation
	@Override
	@Nonnull
	public int[] getSlotsForFace(@Nonnull EnumFacing side)
	{
		return this.SLOTS;
	}

	@Override
	public boolean canInsertItem(int index, @Nonnull ItemStack itemStackIn, @Nonnull EnumFacing direction)
	{
		return index != this.getUpgradeSlotIndex() && this.isItemValidForSlot(index, itemStackIn);
	}

	@Override
	public boolean canExtractItem(int index, @Nonnull ItemStack stack, @Nonnull EnumFacing direction)
	{
		return this.getUpgradeSlotIndex() != index;
	}
}
