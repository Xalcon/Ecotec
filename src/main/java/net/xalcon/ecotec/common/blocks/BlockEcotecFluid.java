package net.xalcon.ecotec.common.blocks;

import net.minecraft.block.material.Material;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import net.minecraftforge.fluids.BlockFluidClassic;
import net.xalcon.ecotec.common.fluids.FluidMFBase;
import net.xalcon.ecotec.common.tileentities.IAutoRegisterTileEntity;
import net.xalcon.ecotec.common.tileentities.logistics.TileEntityFluidPipe;

import javax.annotation.Nullable;

public class BlockEcotecFluid extends BlockFluidClassic implements IAutoRegisterTileEntity
{
	public BlockEcotecFluid(FluidMFBase fluid)
	{
		super(fluid, Material.WATER);
	}

	@Override
	public Class<? extends TileEntity> getTileEntityClass()
	{
		return TileEntityFluidPipe.class;
	}

	@Nullable
	@Override
	public TileEntity createNewTileEntity(World worldIn, int meta)
	{
		return new TileEntityFluidPipe();
	}
}
