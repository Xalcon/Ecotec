package net.xalcon.ecotec.common.tileentities;

public abstract class TileEntityMachineWorldInteractive extends TileEntityMachinePowered
{
	private final int baseRadius;

	protected TileEntityMachineWorldInteractive(int inventorySize)
	{
		this(inventorySize, 1, false);
	}

	protected TileEntityMachineWorldInteractive(int inventorySize, int baseRadius, boolean isRangeUpgradeable)
	{
		super(inventorySize);
		this.baseRadius = baseRadius;
	}

	public int getWorkRadius()
	{
		return this.baseRadius;
	}
}
