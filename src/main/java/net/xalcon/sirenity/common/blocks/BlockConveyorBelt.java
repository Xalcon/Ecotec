package net.xalcon.sirenity.common.blocks;

import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.MoverType;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.xalcon.sirenity.common.ModBlocks;
import net.xalcon.sirenity.common.blocks.properties.EnumElevation;
import net.xalcon.sirenity.common.blocks.properties.PropertyElevation;
import net.xalcon.sirenity.common.creativetabs.CreativeTabSirenityMachines;
import net.xalcon.sirenity.common.util.BlockMath;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class BlockConveyorBelt extends BlockBase
{
	protected static final AxisAlignedBB FLAT_AABB = new AxisAlignedBB(0.0D, 0.0D, 0.0D, 1.0D, 0.0625D, 1.0D);
	protected static final AxisAlignedBB ASCENDING_AABB = new AxisAlignedBB(0.0D, 0.0D, 0.0D, 1.0D, 0.5D, 1.0D);

	public static final PropertyDirection DIRECTION = PropertyDirection.create("direction", EnumFacing.Plane.HORIZONTAL);
	public static final PropertyElevation ELEVATION = PropertyElevation.create("elevation");

	public BlockConveyorBelt()
	{
		super(Material.GROUND, "conveyor_belt");
		this.setCreativeTab(CreativeTabSirenityMachines.Instance);
	}

	@Override
	public boolean isOpaqueCube(IBlockState state)
	{
		return false;
	}

	@Override
	public boolean isFullCube(IBlockState state)
	{
		return false;
	}

	@Override
	public boolean canPlaceBlockAt(World worldIn, BlockPos pos)
	{
		return worldIn.getBlockState(pos.down()).isSideSolid(worldIn, pos.down(), EnumFacing.UP);
	}

	@Nullable
	@Override
	public AxisAlignedBB getCollisionBoundingBox(IBlockState blockState, IBlockAccess worldIn, BlockPos pos)
	{
		return NULL_AABB;
	}

	public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos)
	{
		return state.getValue(ELEVATION) == EnumElevation.FLAT ? FLAT_AABB : ASCENDING_AABB;
	}

	@Override
	public void onEntityCollidedWithBlock(World worldIn, BlockPos pos, IBlockState state, Entity entityIn)
	{
		super.onEntityCollidedWithBlock(worldIn, pos, state, entityIn);
		if(entityIn.isSneaking()) return;
		switch (state.getValue(DIRECTION))
		{
			case NORTH:
				entityIn.motionZ = 0.2;
				break;
			case SOUTH:
				entityIn.motionZ = -0.2;
				break;
			case WEST:
				entityIn.motionX = 0.2;
				break;
			case EAST:
				entityIn.motionX = -0.2;
				break;
		}

		switch(state.getValue(ELEVATION))
		{
			case ASCENDING:
				entityIn.move(MoverType.PISTON, 0, 0.1, 0);
				break;
			case DESCENDING:
				break;
		}
	}

	@Override
	@Nonnull
	@SuppressWarnings("deprecation")
	public IBlockState getStateForPlacement(World worldIn, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer)
	{
		EnumElevation elevation = EnumElevation.FLAT;
		if(worldIn.getBlockState(pos.offset(placer.getHorizontalFacing()).up()).getBlock() == ModBlocks.ConveyorBelt)
			elevation = EnumElevation.ASCENDING;
		else if(worldIn.getBlockState(pos.offset(placer.getHorizontalFacing().getOpposite()).up()).getBlock() == ModBlocks.ConveyorBelt)
			elevation = EnumElevation.DESCENDING;
		return this.getDefaultState().withProperty(ELEVATION, elevation).withProperty(DIRECTION, placer.getHorizontalFacing().getOpposite());
	}

	@Override
	@Nonnull
	@SuppressWarnings("deprecation") // see https://github.com/MinecraftForge/MinecraftForge/issues/3311
	public IBlockState getStateFromMeta(int meta)
	{
		EnumFacing direction = EnumFacing.values()[((meta & 0b1100) >> 2) + 2];
		EnumElevation elevation = EnumElevation.values()[meta & 0b0011];

		return this.getDefaultState().withProperty(ELEVATION, elevation).withProperty(DIRECTION, direction);
	}

	@Override
	public int getMetaFromState(IBlockState state)
	{
		int direction = state.getValue(DIRECTION).ordinal() - 2;
		int elevation = state.getValue(ELEVATION).ordinal();
		return (direction << 2) | elevation;
	}

	@Override
	@Nonnull
	protected BlockStateContainer createBlockState()
	{
		return new BlockStateContainer(this, ELEVATION, DIRECTION);
	}
}
