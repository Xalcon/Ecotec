package net.xalcon.minefactory.common.blocks;

import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.xalcon.minefactory.common.creativetabs.CreativeTabMinefactoryMachines;
import net.xalcon.minefactory.common.tileentities.IAutoRegisterTileEntity;

import javax.annotation.Nullable;

public abstract class BlockMachineBase extends BlockBase implements ITileEntityProvider, IAutoRegisterTileEntity
{
	public static final PropertyDirection FACING = PropertyDirection.create("facing", EnumFacing.Plane.HORIZONTAL);

	public BlockMachineBase(String internalName)
	{
		this(Material.IRON, internalName);
	}

	public BlockMachineBase(Material materialIn, String internalName)
	{
		super(materialIn, internalName);
		this.setDefaultState(this.blockState.getBaseState().withProperty(FACING, EnumFacing.NORTH));
		this.setCreativeTab(CreativeTabMinefactoryMachines.Instance);
	}

	@Nullable
	@Override
	public abstract TileEntity createNewTileEntity(World worldIn, int meta);

	@Override
	@SuppressWarnings("deprecation")
	public IBlockState getStateForPlacement(World worldIn, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer)
	{
		return this.getDefaultState().withProperty(FACING, placer.getHorizontalFacing().getOpposite());
	}

	@Override
	@SuppressWarnings("deprecation") // see https://github.com/MinecraftForge/MinecraftForge/issues/3311
	public IBlockState getStateFromMeta(int meta)
	{
		EnumFacing enumfacing = EnumFacing.getFront(meta);

		if (enumfacing.getAxis() == EnumFacing.Axis.Y)
		{
			enumfacing = EnumFacing.NORTH;
		}

		return this.getDefaultState().withProperty(FACING, enumfacing);
	}

	@Override
	public int getMetaFromState(IBlockState state)
	{
		return state.getValue(FACING).getIndex();
	}

	@Override
	protected BlockStateContainer createBlockState()
	{
		return new BlockStateContainer(this, FACING);
	}

	@Override
	public String getTileEntityRegistryName()
	{
		return "tile." + this.getInternalName();
	}
}
