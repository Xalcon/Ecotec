package net.xalcon.ecotec.common.blocks.logistics;

import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraftforge.common.property.ExtendedBlockState;
import net.minecraftforge.common.property.IExtendedBlockState;
import net.minecraftforge.common.property.IUnlistedProperty;
import net.xalcon.ecotec.common.blocks.BlockBase;
import net.xalcon.ecotec.common.blocks.properties.UnlistedPropertyObject;
import net.xalcon.ecotec.common.tileentities.logistics.TileEntityFluidPipe;

import javax.annotation.Nonnull;

public class BlockFluidPipe extends BlockBase
{
	public final static UnlistedPropertyObject<TileEntityFluidPipe> TILE_ENTITY = new UnlistedPropertyObject<>("tile", TileEntityFluidPipe.class);

	public BlockFluidPipe()
	{
		super(Material.IRON, "fluid_pipe");
	}

	@Override
	public boolean isFullCube(IBlockState state)
	{
		return false;
	}

	@Override
	public boolean isFullBlock(IBlockState state)
	{
		return false;
	}

	@Override
	public boolean isOpaqueCube(IBlockState state)
	{
		return false;
	}

	@Nonnull
	@Override
	public BlockRenderLayer getBlockLayer()
	{
		return BlockRenderLayer.CUTOUT;
	}

	@Override
	protected BlockStateContainer createBlockState()
	{
		return new ExtendedBlockState(this, new IProperty[0], new IUnlistedProperty[] { TILE_ENTITY });
	}

	@Override
	public IBlockState getExtendedState(IBlockState state, IBlockAccess world, BlockPos pos)
	{
		IExtendedBlockState exState = (IExtendedBlockState) super.getExtendedState(state, world, pos);
		TileEntity te = world.getTileEntity(pos);
		if(te instanceof TileEntityFluidPipe)
			return exState.withProperty(TILE_ENTITY, (TileEntityFluidPipe) te);
		return exState;
	}
}
