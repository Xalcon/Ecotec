package net.xalcon.ecotec.common.tileentities;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ITickable;

public abstract class TileEntityTicking extends TileEntityBaseNew implements ITickable
{
	//region Ticking logic
	private int idleTime;
	private int updateCountdown;

	@Override
	public final void update()
	{
		// we are always dirty - lol
		this.markDirty();

		// reduce idle timer on server and on the client. this might look funny on lagging servers, but
		// this way we only send 1 update packet per work cycle if there is nothing to do
		this.idleTime = Math.max(0, this.idleTime - 1);

		if (!this.getWorld().isRemote)
		{
			this.updateCountdown--;

			if (this.idleTime <= 0)
			{
				if (!this.doWork())
					this.idleTime = this.getMaxIdleTime();
			}

			if(this.updateCountdown == 0)
			{
				this.sendUpdate(false);
			}
		}
	}

	/**
	 * will be run if the idle time reached 0
	 * @return true on work done, otherwise false. If false: idle time will bet set to {@link #getMaxIdleTime()}
	 */
	protected abstract boolean doWork();

	/**
	 * Can be called to schedule a partial sync at the end of the tick
	 */
	public void markForUpdate()
	{
		this.updateCountdown = this.updateCountdown >= 0 ? this.updateCountdown : 5;
	}

	/**
	 * returns the maximum amount of idle time between each work cycle
	 * @return time in ticks
	 */
	public short getMaxIdleTime() { return 200; }

	/**
	 * returns the idle time until the next work cycle starts
	 * @return idle time in ticks
	 */
	public int getIdleTime() { return this.idleTime; }

	/**
	 * sets the idle time for the current work cycle
	 * @param time idle time between 0 and {@link #getMaxIdleTime()} (will be automatically clamped)
	 */
	public void setIdleTime(short time) { this.idleTime = Math.max(0, Math.min(time, this.getMaxIdleTime())); }
	//endregion

	//region NBT handling
	@Override
	public void readSyncNbt(NBTTagCompound nbt, NbtSyncType type)
	{
		super.readSyncNbt(nbt, type);
		this.idleTime = nbt.getShort("eco:idle");
	}

	@Override
	public void writeSyncNbt(NBTTagCompound nbt, NbtSyncType type)
	{
		super.writeSyncNbt(nbt, type);
		nbt.setShort("eco:idle", (short) this.idleTime);
	}
	//endregion
}
