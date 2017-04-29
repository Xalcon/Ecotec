package net.xalcon.ecotec.common.tileentities.machines;

import net.xalcon.ecotec.common.init.ModBlocks;
import net.xalcon.ecotec.common.tileentities.TileEntityMachinePowered;

public class TileEntityMachineAutoDisenchanter extends TileEntityMachinePowered
{
	public TileEntityMachineAutoDisenchanter()
	{
		super(4);
	}

	@Override
	public String getUnlocalizedName()
	{
		return ModBlocks.MachineAutoDisenchanter.getUnlocalizedName();
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
