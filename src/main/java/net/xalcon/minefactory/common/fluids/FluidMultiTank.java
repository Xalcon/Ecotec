package net.xalcon.minefactory.common.fluids;

import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTank;
import net.minecraftforge.fluids.capability.FluidTankPropertiesWrapper;
import net.minecraftforge.fluids.capability.IFluidHandler;
import net.minecraftforge.fluids.capability.IFluidTankProperties;

import javax.annotation.Nullable;
import java.util.Arrays;
import java.util.Optional;

public class FluidMultiTank implements IFluidHandler
{
	private FluidTank[] tanks;

	public FluidMultiTank(FluidTank... tanks)
	{
		this.tanks = new FluidTank[tanks.length];
		System.arraycopy(tanks, 0, this.tanks, 0, tanks.length);
	}

	@Override
	public IFluidTankProperties[] getTankProperties()
	{
		IFluidTankProperties[] props = new IFluidTankProperties[tanks.length];
		for (int i = 0; i < this.tanks.length; i++)
			props[i] = new FluidTankPropertiesWrapper(this.tanks[i]);
		return props;
	}

	@Override
	public int fill(@Nullable FluidStack resource, boolean doFill)
	{
		if (resource == null) return 0;

		Optional<FluidTank> tank = Arrays.stream(tanks).filter(t -> t.canFillFluidType(resource)).findFirst();
		int fill = 0;
		if (tank.isPresent())
		{
			fill = tank.get().fill(resource, doFill);
			if (doFill)
				System.out.printf("Filled %d into %s tank (%d / %d)", fill, resource.getLocalizedName(), tank.get().getFluidAmount(), tank.get().getCapacity());
		}
		return fill;
	}

	@Override
	public FluidStack drain(@Nullable FluidStack resource, boolean doDrain)
	{
		Optional<FluidTank> tank = Arrays.stream(tanks).filter(t -> t.canDrainFluidType(resource)).findFirst();
		return tank.isPresent() ? tank.get().drain(resource, doDrain) : null;
	}

	@Override
	public FluidStack drain(int maxDrain, boolean doDrain)
	{
		Optional<FluidTank> tank = Arrays.stream(tanks).filter(t -> t.canDrain() && t.getFluidAmount() > 0).findFirst();
		return tank.isPresent() ? tank.get().drain(maxDrain, doDrain) : null;
	}
}
