package net.xalcon.ecotec.common.blocks.world;

import net.minecraft.block.BlockLog;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.xalcon.ecotec.common.CreativeTabEcotec;
import net.xalcon.ecotec.common.blocks.BlockBase;

import javax.annotation.Nonnull;

public class BlockRubberTreeLog extends BlockBase
{
	public BlockRubberTreeLog()
	{
		super(Material.WOOD, "rubber_tree_log");
		this.setCreativeTab(CreativeTabEcotec.Instance);
	}

	@Override
	@Nonnull
	protected BlockStateContainer createBlockState()
	{
		return new BlockStateContainer(this, BlockLog.LOG_AXIS);
	}

	@Override
	@SuppressWarnings("deprecation")
	@Nonnull
	public IBlockState getStateForPlacement(World worldIn, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer)
	{
		return this.getStateFromMeta(meta).withProperty(BlockLog.LOG_AXIS, BlockLog.EnumAxis.fromFacingAxis(facing.getAxis()));
	}

	@Override public boolean canSustainLeaves(IBlockState state, net.minecraft.world.IBlockAccess world, BlockPos pos){ return true; }
	@Override public boolean isWood(net.minecraft.world.IBlockAccess world, BlockPos pos){ return true; }
	/**
	 * Convert the given metadata into a BlockState for this Block
	 */
	@SuppressWarnings("deprecation")
	@Nonnull
	public IBlockState getStateFromMeta(int meta)
	{
		BlockLog.EnumAxis axis = BlockLog.EnumAxis.Y;
		int i = meta & 12;

		if (i == 4)
		{
			axis = BlockLog.EnumAxis.X;
		}
		else if (i == 8)
		{
			axis = BlockLog.EnumAxis.Z;
		}

		return this.getDefaultState().withProperty(BlockLog.LOG_AXIS, axis);
	}

	/**
	 * Convert the BlockState into the correct metadata value
	 */
	public int getMetaFromState(IBlockState state)
	{
		int i = 0;
		BlockLog.EnumAxis axis = state.getValue(BlockLog.LOG_AXIS);

		if (axis == BlockLog.EnumAxis.X)
		{
			i |= 4;
		}
		else if (axis == BlockLog.EnumAxis.Z)
		{
			i |= 8;
		}

		return i;
	}
}
