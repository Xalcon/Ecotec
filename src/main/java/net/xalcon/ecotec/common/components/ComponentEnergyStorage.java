package net.xalcon.ecotec.common.components;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.energy.IEnergyStorage;
import net.xalcon.ecotec.api.components.IEcotecComponent;
import net.xalcon.ecotec.common.tileentities.NbtSyncType;
import net.xalcon.ecotec.common.tileentities.TileEntityBaseNew;
import net.xalcon.ecotec.common.tileentities.TileEntityTickable;

import javax.annotation.Nonnull;

public class ComponentEnergyStorage implements IEnergyStorage, IEcotecComponent
{
	private int energyStored;
	private int maxEnergyIn;
	private int maxEnergyOut;
	private int maxEnergyStored;
	private Runnable onUpdateRunnable;

	public ComponentEnergyStorage(int maxEnergyIn, int maxEnergyOut, int maxEnergyStored)
	{
		this(0, maxEnergyIn, maxEnergyOut, maxEnergyStored);
	}

	public ComponentEnergyStorage(int energyStored, int maxEnergyIn, int maxEnergyOut, int maxEnergyStored)
	{
		if(maxEnergyStored < 0)
			maxEnergyStored = 0;
		if(energyStored < 0)
			energyStored = 0;
		else if(energyStored > maxEnergyStored)
			energyStored = maxEnergyStored;
		if(maxEnergyIn < 0)
			maxEnergyIn = 0;
		if(maxEnergyOut < 0)
			maxEnergyOut = 0;

		this.energyStored = energyStored;
		this.maxEnergyIn = maxEnergyIn;
		this.maxEnergyOut = maxEnergyOut;
		this.maxEnergyStored = maxEnergyStored;
	}

	@Override
	public int receiveEnergy(int maxReceive, boolean simulate)
	{
		if(this.maxEnergyIn == 0) return 0;
		int input = Math.max(0, Math.min(this.maxEnergyStored - this.energyStored, Math.min(this.maxEnergyIn, maxReceive)));
		if(!simulate)
		{
			this.energyStored += input;
			this.onContentChanged();
		}
		return input;
	}

	@Override
	public int extractEnergy(int maxExtract, boolean simulate)
	{
		if(this.maxEnergyOut > 0) return 0;
		int output = Math.max(0, Math.min(this.energyStored, Math.min(this.maxEnergyOut, maxExtract)));
		if(!simulate)
		{
			this.maxEnergyStored -= output;
			this.onContentChanged();
		}
		return output;
	}

	@Override
	public int getEnergyStored()
	{
		return this.energyStored;
	}

	public void setEnergyStored(int energyStored)
	{
		if(this.energyStored == energyStored) return;
		this.energyStored = energyStored;
		this.onContentChanged();
	}

	@Override
	public int getMaxEnergyStored()
	{
		return this.maxEnergyStored;
	}

	public void setMaxEnergyStored(int maxEnergyStored)
	{
		if(this.maxEnergyStored == maxEnergyStored) return;
		this.maxEnergyStored = maxEnergyStored;
		this.onContentChanged();
	}

	public int getMaxEnergyIn()
	{
		return this.maxEnergyIn;
	}

	public void setMaxEnergyIn(int maxEnergyIn)
	{
		this.maxEnergyIn = maxEnergyIn;
	}

	public int getMaxEnergyOut()
	{
		return this.maxEnergyOut;
	}

	public void setMaxEnergyOut(int maxEnergyOut)
	{
		this.maxEnergyOut = maxEnergyOut;
	}

	@Override
	public boolean canExtract()
	{
		return this.maxEnergyOut > 0;
	}

	@Override
	public boolean canReceive()
	{
		return this.maxEnergyIn > 0;
	}

	public void onContentChanged()
	{
		if(this.onUpdateRunnable != null)
			this.onUpdateRunnable.run();
	}

	//region IEcotecComponent implementation
	@Override
	public void initialize(ICapabilityProvider provider)
	{
		// TODO: implement update lambda as a component
		if(provider instanceof TileEntityTickable)
			this.onUpdateRunnable = ((TileEntityTickable) provider)::markForUpdate;
		else if(provider instanceof TileEntityBaseNew)
			this.onUpdateRunnable = () -> ((TileEntityBaseNew) provider).sendUpdate(false);
		else
			this.onUpdateRunnable = null;
	}

	@Override
	public void invalidate()
	{
		this.onUpdateRunnable = null;
	}

	@Override
	public void readSyncNbt(@Nonnull NBTTagCompound nbt, @Nonnull NbtSyncType type)
	{
		NBTTagCompound energyNbt = nbt.getCompoundTag("eco:power");
		this.energyStored = energyNbt.getInteger("stored");
		if(this.energyStored < 0)
			this.energyStored = 0;
		else if(this.energyStored > this.maxEnergyStored)
			this.energyStored = this.maxEnergyStored;
	}

	@Override
	public void writeSyncNbt(@Nonnull NBTTagCompound nbt, @Nonnull NbtSyncType type)
	{
		NBTTagCompound energyNbt = new NBTTagCompound();
		energyNbt.setInteger("stored", this.energyStored);
		nbt.setTag("eco:power", energyNbt);
	}
	//endregion
}
