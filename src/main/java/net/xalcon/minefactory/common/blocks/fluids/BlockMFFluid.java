package net.xalcon.minefactory.common.blocks.fluids;

import net.minecraft.block.material.Material;
import net.minecraftforge.fluids.BlockFluidClassic;
import net.minecraftforge.fluids.BlockFluidFinite;
import net.minecraftforge.fluids.Fluid;
import net.xalcon.minefactory.common.fluids.FluidMFBase;

public class BlockMFFluid extends BlockFluidClassic
{
	public BlockMFFluid(FluidMFBase fluid)
	{
		super(fluid, Material.WATER);
	}
}