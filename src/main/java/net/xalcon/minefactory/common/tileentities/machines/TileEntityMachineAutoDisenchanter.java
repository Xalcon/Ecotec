package net.xalcon.minefactory.common.tileentities.machines;

import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.xalcon.minefactory.common.tileentities.TileEntityWithInventory;

public class TileEntityMachineAutoDisenchanter extends TileEntityWithInventory
{
	@Override
	public String getName()
	{
		return "machine_auto_disenchanter";
	}

	@Override
	public int[] getSlotsForFace(EnumFacing side)
	{
		return new int[0];
	}

	@Override
	public boolean canInsertItem(int index, ItemStack itemStackIn, EnumFacing direction)
	{
		return false;
	}

	@Override
	public boolean canExtractItem(int index, ItemStack stack, EnumFacing direction)
	{
		return false;
	}
}