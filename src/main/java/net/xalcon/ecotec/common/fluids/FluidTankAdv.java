package net.xalcon.ecotec.common.fluids;

import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTank;
import net.xalcon.ecotec.common.tileentities.TileEntityTickable;
import net.xalcon.ecotec.common.tileentities.TileEntityBase;

import javax.annotation.Nullable;

public class FluidTankAdv extends FluidTank
{
	public FluidTankAdv(TileEntity tile, Fluid fluid, int amount, int capacity)
	{
		super(fluid, amount, capacity);
		this.tile = tile;
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

	@Override
	protected void onContentsChanged()
	{
		if (this.tile != null)
		{
			this.tile.markDirty();
			if(this.tile instanceof TileEntityTickable)
				((TileEntityTickable)this.tile).markForUpdate();
			if(this.tile instanceof TileEntityBase)
				((TileEntityBase)this.tile).sendUpdate(false);
		}
	}
}
