package net.xalcon.minefactory.common.tileentities.machines;

import net.minecraft.util.ITickable;
import net.xalcon.minefactory.common.tileentities.TileEntityMachineBase;

import javax.annotation.Nonnull;

public class TileEntityMachineRancher extends TileEntityMachineBase implements ITickable
{
	public TileEntityMachineRancher()
	{
		super(9);
	}

	@Nonnull
	@Override
	public String getName()
	{
		return "machine_rancher";
	}

	@Override
	public void update()
	{

	}
}
