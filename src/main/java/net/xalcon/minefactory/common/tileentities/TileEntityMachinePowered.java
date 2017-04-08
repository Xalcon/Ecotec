package net.xalcon.minefactory.common.tileentities;

import net.minecraft.util.ITickable;

public abstract class TileEntityMachinePowered extends TileEntityMachineInventory implements ITickable
{
	private int idleTicks;
	private int workTicks;

	protected TileEntityMachinePowered(int inventorySize)
	{
		super(inventorySize);
	}

	@Override
	public final void update()
	{
		this.idleTicks = Math.max(0, this.idleTicks - 1);
		if(!this.getWorld().isRemote)
		{
			this.idleTicks = this.doWork() ? 0 : this.getMaxIdleTicks();
		}
	}

	public final int getIdleTicks() { return this.idleTicks; }
	protected final void setIdleTicks(int ticks) { this.idleTicks = ticks; }
	public abstract int getMaxIdleTicks();

	protected final void incrementWorkProgress() { this.workTicks++; }
	protected final void incrementWorkProgress(int ticks) { this.workTicks += ticks; }
	public final int getWorkProgress() { return this.workTicks; }
	protected final void setWorkProgress(int ticks) { this.idleTicks = ticks; }
	public abstract int getMaxProgressTicks();

	protected abstract boolean doWork();
}
