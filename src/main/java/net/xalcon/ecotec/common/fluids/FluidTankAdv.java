package net.xalcon.ecotec.common.fluids;

import net.minecraft.block.state.IBlockState;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTank;

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
			IBlockState state = this.tile.getWorld().getBlockState(this.tile.getPos());
			this.tile.getWorld().notifyBlockUpdate(this.tile.getPos(), state, state, 0);
		}
	}
}
