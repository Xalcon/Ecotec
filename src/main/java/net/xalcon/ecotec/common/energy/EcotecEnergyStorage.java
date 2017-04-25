package net.xalcon.ecotec.common.energy;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.common.util.INBTSerializable;
import net.minecraftforge.energy.IEnergyStorage;

public class EcotecEnergyStorage implements IEnergyStorage, INBTSerializable<NBTTagCompound>
{
	private int energyStored;
	private int maxEnergyIn;
	private int maxEnergyOut;
	private int maxEnergyStored;

	public EcotecEnergyStorage(int maxEnergyIn, int maxEnergyOut, int maxEnergyStored)
	{
		this(0, maxEnergyIn, maxEnergyOut, maxEnergyStored);
	}

	public EcotecEnergyStorage(int energyStored, int maxEnergyIn, int maxEnergyOut, int maxEnergyStored)
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
			this.energyStored += input;
		return input;
	}

	@Override
	public int extractEnergy(int maxExtract, boolean simulate)
	{
		if(this.maxEnergyOut > 0) return 0;
		int output = Math.max(0, Math.min(this.energyStored, Math.min(this.maxEnergyOut, maxExtract)));
		if(!simulate)
			this.maxEnergyStored -= output;
		return output;
	}

	@Override
	public int getEnergyStored()
	{
		return this.energyStored;
	}

	public void setEnergyStored(int energyStored)
	{
		this.energyStored = energyStored;
	}

	@Override
	public int getMaxEnergyStored()
	{
		return this.maxEnergyStored;
	}

	public void setMaxEnergyStored(int maxEnergyStored)
	{
		this.maxEnergyStored = maxEnergyStored;
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


	//region INBTSerializable<NBTTagCompound> implementation
	@Override
	public NBTTagCompound serializeNBT()
	{
		NBTTagCompound nbt = new NBTTagCompound();
		nbt.setInteger("stored", this.energyStored);
		return nbt;
	}

	@Override
	public void deserializeNBT(NBTTagCompound nbt)
	{
		this.energyStored = nbt.getInteger("stored");
		if(this.energyStored < 0)
			this.energyStored = 0;
		else if(this.energyStored > this.maxEnergyStored)
			this.energyStored = this.maxEnergyStored;
	}
	//endregion
}
