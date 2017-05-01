package net.xalcon.ecotec.common.tileentities;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.energy.CapabilityEnergy;
import net.xalcon.ecotec.common.components.ComponentEnergyStorage;

import javax.annotation.Nullable;

public abstract class TileEntityMachinePowered extends TileEntityMachine implements ITickable
{
	private int idleTicks;
	private int workTicks;
	private ComponentEnergyStorage energyStorage;

	protected TileEntityMachinePowered()
	{
		super();
		this.energyStorage = new ComponentEnergyStorage(1024, 0, 16000, () -> this.sendUpdate(false));
	}

	@Override
	public final void update()
	{
		this.idleTicks = Math.max(0, this.idleTicks - 1);
		this.markDirty();
		if (!this.getWorld().isRemote)
		{
			// TODO: Add "failedItems" idle ticks so we dont fuck up server tps if the inventory is clogged up
			if (this.isInventoryClogged() && !this.dropFailedItems())
				this.setIdleTicks(this.getMaxIdleTicks());

			if (this.idleTicks <= 0)
			{
				if (!this.doWork())
					this.idleTicks = this.getMaxIdleTicks();

				/*IBlockState state = this.world.getBlockState(this.pos);
				this.world.notifyBlockUpdate(this.pos, state, state, 0);*/

				//this.sendUpdate(false);
			}
		}
	}

	@Override
	public void readSyncNbt(NBTTagCompound compound, NbtSyncType type)
	{
		super.readSyncNbt(compound, type);
		this.idleTicks = compound.getInteger("eco:idle");
		this.workTicks = compound.getInteger("eco:work");
		NBTTagCompound powerNbt = compound.getCompoundTag("eco:power");
		if(!powerNbt.hasNoTags())
		{
			this.energyStorage.readSyncNbt(compound, type);
		}
	}

	@Override
	public void writeSyncNbt(NBTTagCompound compound, NbtSyncType type)
	{
		super.writeSyncNbt(compound, type);
		compound.setInteger("eco:idle", this.idleTicks);
		compound.setInteger("eco:work", this.workTicks);
		this.energyStorage.writeSyncNbt(compound, type);
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

	@Override
	public boolean saveNbtOnDrop() { return true; }

	@Override
	public boolean hasCapability(Capability<?> capability, @Nullable EnumFacing facing)
	{
		return capability == CapabilityEnergy.ENERGY || super.hasCapability(capability, facing);
	}

	@Nullable
	@Override
	public <T> T getCapability(Capability<T> capability, @Nullable EnumFacing facing)
	{
		return capability == CapabilityEnergy.ENERGY
				? CapabilityEnergy.ENERGY.cast(this.energyStorage)
				: super.getCapability(capability, facing);
	}
}
