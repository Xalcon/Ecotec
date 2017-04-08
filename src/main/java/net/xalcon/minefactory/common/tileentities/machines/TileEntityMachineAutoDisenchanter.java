package net.xalcon.minefactory.common.tileentities.machines;

import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.xalcon.minefactory.common.tileentities.TileEntityBase;
import net.xalcon.minefactory.common.tileentities.TileEntityMachineInventory;

public class TileEntityMachineAutoDisenchanter extends TileEntityMachineInventory
{
	public TileEntityMachineAutoDisenchanter()
	{
		super(4);
	}

	@Override
	public String getUnlocalizedName()
	{
		return "machine_auto_disenchanter";
	}
}
