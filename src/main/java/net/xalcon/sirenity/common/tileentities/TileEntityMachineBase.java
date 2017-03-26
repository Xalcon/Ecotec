package net.xalcon.sirenity.common.tileentities;

import net.minecraft.item.ItemStack;

public abstract class TileEntityMachineBase extends TileEntityWithInventory
{
	public int getUpgradeSlotIndex()
	{
		return 0;
	}

	public ItemStack getUpgradeSlotItemStack()
	{
		return this.inventory.get(this.getUpgradeSlotIndex());
	}

	public TileEntityMachineBase()
	{
		this.inventory.add(ItemStack.EMPTY);
	}
}
