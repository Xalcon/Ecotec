package net.xalcon.ecotec.common.fluids;

import net.minecraft.block.state.IBlockState;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTank;
import net.xalcon.ecotec.common.tileentities.TileEntityBase;

import javax.annotation.Nullable;

public class FluidTankAdv extends FluidTank
{
	public FluidTankAdv(TileEntityBase tile, Fluid fluid, int amount, int capacity)
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
			// TODO: implement "schedule update" to avoid sending multiple updates per tick
			((TileEntityBase)this.tile).sendUpdate(false);
		}
	}
}
