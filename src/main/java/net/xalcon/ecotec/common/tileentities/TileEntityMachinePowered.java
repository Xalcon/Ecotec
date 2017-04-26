package net.xalcon.ecotec.common.tileentities;

import net.minecraft.block.state.IBlockState;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.energy.CapabilityEnergy;
import net.xalcon.ecotec.common.energy.EcotecEnergyStorage;

import javax.annotation.Nullable;

public abstract class TileEntityMachinePowered extends TileEntityMachine implements ITickable
{
	private int idleTicks;
	private int workTicks;
	private EcotecEnergyStorage energyStorage;

	protected TileEntityMachinePowered(int inventorySize)
	{
		super(inventorySize);
		this.energyStorage = new EcotecEnergyStorage(1024, 0, 16000);
	}

	boolean emptying;

	@Override
	public final void update()
	{
		this.idleTicks = Math.max(0, this.idleTicks - 1);
		this.markDirty();
		if (!this.getWorld().isRemote)
		{
			if(!this.emptying)
			{
				this.energyStorage.setEnergyStored(this.energyStorage.getEnergyStored() + 100);
				if(this.energyStorage.getEnergyStored() >= this.energyStorage.getMaxEnergyStored())
					this.emptying = true;

				IBlockState state = this.getWorld().getBlockState(this.getPos());
				this.getWorld().notifyBlockUpdate(this.getPos(), state, state, 4);
			}
			else
			{
				this.energyStorage.setEnergyStored(this.energyStorage.getEnergyStored() - 100);
				if(this.energyStorage.getEnergyStored() <= 0)
					this.emptying = false;

				IBlockState state = this.getWorld().getBlockState(this.getPos());
				this.getWorld().notifyBlockUpdate(this.getPos(), state, state, 4);
			}

			// TODO: Add "failedItems" idle ticks so we dont fuck up server tps if the inventory is clogged up
			if (this.isInventoryClogged() && !this.dropFailedItems())
				this.setIdleTicks(this.getMaxIdleTicks());

			if (this.idleTicks <= 0)
			{
				if (!this.doWork())
					this.idleTicks = this.getMaxIdleTicks();

				IBlockState state = this.getWorld().getBlockState(this.getPos());
				this.getWorld().notifyBlockUpdate(this.getPos(), state, state, 0);
			}
		}
	}

	@Override
	public void readFromNBT(NBTTagCompound compound)
	{
		super.readFromNBT(compound);
		this.idleTicks = compound.getInteger("eco:idle");
		this.workTicks = compound.getInteger("eco:work");
		NBTTagCompound powerNbt = compound.getCompoundTag("eco:power");
		if(!powerNbt.hasNoTags())
		{
			this.energyStorage.deserializeNBT(powerNbt);
		}
	}

	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound compound)
	{
		compound.setInteger("eco:idle", this.idleTicks);
		compound.setInteger("eco:work", this.workTicks);
		compound.setTag("eco:power", this.energyStorage.serializeNBT());
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
