package net.xalcon.ecotec.common.components;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTank;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;
import net.minecraftforge.fluids.capability.IFluidHandler;
import net.xalcon.ecotec.api.components.IEcotecComponent;
import net.xalcon.ecotec.api.components.IStateUpdatable;
import net.xalcon.ecotec.common.init.ModCaps;
import net.xalcon.ecotec.common.tileentities.NbtSyncType;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class ComponentFluidTank extends FluidTank implements IEcotecComponent<IFluidHandler>
{
	private IStateUpdatable updatable;

	public ComponentFluidTank(int capacity)
	{
		super(capacity);
	}

	public ComponentFluidTank(@Nullable FluidStack fluidStack, int capacity)
	{
		super(fluidStack, capacity);
	}

	public ComponentFluidTank(Fluid fluid, int amount, int capacity)
	{
		super(fluid, amount, capacity);
	}

	@Override
	public void initialize(ICapabilityProvider provider)
	{
		this.updatable = provider.getCapability(ModCaps.getStateUpdatableCap(), null);
	}

	@Override
	public void invalidate()
	{
		this.updatable = null;
	}

	@Override
	public Capability<IFluidHandler> getCapability()
	{
		return CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY;
	}

	@Override
	public boolean canFillFluidType(FluidStack fluid)
	{
		return this.canFill() && (this.getFluid() == null || fluid.getFluid() == this.getFluid().getFluid());
	}

	@Override
	public boolean canDrainFluidType(@Nullable FluidStack fluid)
	{
		return this.canDrain() && this.getFluid() != null && (fluid == null || this.getFluid().getFluid() == fluid.getFluid());
	}

	public void useFluid(int amount)
	{
		if(this.fluid != null)
		{
			this.fluid.amount -= amount;
			this.onContentsChanged();
		}
	}

	@Override
	protected void onContentsChanged()
	{
		if(this.updatable == null) return;
		this.updatable.scheduleUpdate();
	}

	@Override
	public void readSyncNbt(@Nonnull NBTTagCompound nbt, @Nonnull NbtSyncType type)
	{
		this.readFromNBT(nbt.getCompoundTag("eco:fluid"));
	}

	@Override
	public void writeSyncNbt(@Nonnull NBTTagCompound nbt, @Nonnull NbtSyncType type)
	{
		NBTTagCompound fluidNbt = new NBTTagCompound();
		this.writeToNBT(fluidNbt);
		nbt.setTag("eco:fluid", fluidNbt);
	}
}
