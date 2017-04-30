package net.xalcon.ecotec.common.tileentities;

public abstract class TileEntityMachineWorldInteractive extends TileEntityMachinePowered
{
	private final int baseRadius;

	protected TileEntityMachineWorldInteractive()
	{
		this(1, false);
	}

	protected TileEntityMachineWorldInteractive(int baseRadius, boolean isRangeUpgradeable)
	{
		this.baseRadius = baseRadius;
	}

	public int getWorkRadius()
	{
		return this.baseRadius;
	}
}
