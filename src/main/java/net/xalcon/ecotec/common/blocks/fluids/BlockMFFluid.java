package net.xalcon.ecotec.common.blocks.fluids;

import net.minecraft.block.material.Material;
import net.minecraftforge.fluids.BlockFluidClassic;
import net.xalcon.ecotec.common.fluids.FluidMFBase;

public class BlockMFFluid extends BlockFluidClassic
{
	public BlockMFFluid(FluidMFBase fluid)
	{
		super(fluid, Material.WATER);
	}
}
