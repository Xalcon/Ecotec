package net.xalcon.minefactory.common.tileentities.machines;

import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.xalcon.minefactory.common.tileentities.TileEntityBase;
import net.xalcon.minefactory.common.tileentities.TileEntityMachineInventory;
import net.xalcon.minefactory.common.tileentities.TileEntityMachinePowered;

public class TileEntityMachineAutoDisenchanter extends TileEntityMachinePowered
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

	@Override
	public int getMaxIdleTicks()
	{
		return 100;
	}

	@Override
	public int getMaxProgressTicks()
	{
		return 200;
	}

	@Override
	protected boolean doWork()
	{
		return false;
	}
}
