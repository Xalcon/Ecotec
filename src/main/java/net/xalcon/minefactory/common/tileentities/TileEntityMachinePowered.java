package net.xalcon.minefactory.common.tileentities;

import net.minecraft.block.state.IBlockState;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ITickable;

public abstract class TileEntityMachinePowered extends TileEntityMachine implements ITickable
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
		this.markDirty();
		if(!this.getWorld().isRemote)
		{
			// TODO: Add "failedItems" idle ticks so we dont fuck up server tps if the inventory is clogged up
			if(this.isInventoryClogged() && !this.dropFailedItems())
				this.setIdleTicks(this.getMaxIdleTicks());

			if(idleTicks <= 0)
			{
				if(!this.doWork())
					this.idleTicks =  this.getMaxIdleTicks();

				IBlockState state = this.getWorld().getBlockState(this.getPos());
				this.getWorld().notifyBlockUpdate(this.getPos(), state, state, 0);
			}
		}
	}

	@Override
	public void readFromNBT(NBTTagCompound compound)
	{
		super.readFromNBT(compound);
		this.idleTicks = compound.getInteger("mfr:idle");
		this.workTicks = compound.getInteger("mfr:work");
	}

	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound compound)
	{
		compound.setInteger("mfr:idle", this.idleTicks);
		compound.setInteger("mfr:work", this.workTicks);
		return super.writeToNBT(compound);
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
