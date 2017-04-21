package net.xalcon.ecotec.common.tileentities.agriculture;

import net.xalcon.ecotec.common.tileentities.TileEntityMachineWorldInteractive;

public class TileEntityMachinePlanter extends TileEntityMachineWorldInteractive
{
	public TileEntityMachinePlanter()
	{
		super(9);
	}

	@Override
	public String getUnlocalizedName()
	{
		return "machine_planter";
	}

	@Override
	public int getMaxIdleTicks()
	{
		return 100;
	}

	@Override
	public int getMaxProgressTicks()
	{
		return 1;
	}

	@Override
	protected boolean doWork()
	{
		return false;
	}
}
