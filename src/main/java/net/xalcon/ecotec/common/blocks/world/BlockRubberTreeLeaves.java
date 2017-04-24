package net.xalcon.ecotec.common.blocks.world;

import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.xalcon.ecotec.common.blocks.BlockBase;
import net.xalcon.ecotec.common.blocks.IBlockTintable;
import net.xalcon.ecotec.common.creativetabs.CreativeTabEcotecMachines;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class BlockRubberTreeLeaves extends BlockBase implements IBlockTintable
{
	public BlockRubberTreeLeaves()
	{
		super(Material.LEAVES, "rubber_tree_leaves");
		this.setCreativeTab(CreativeTabEcotecMachines.Instance);
	}

	@Override
	public int getColorTint(IBlockState state, @Nullable IBlockAccess worldIn, @Nullable BlockPos pos, int tintIndex)
	{
		return 0xFF66CC00;
	}

	@SideOnly(Side.CLIENT)
	public BlockRenderLayer getBlockLayer()
	{
		return BlockRenderLayer.CUTOUT_MIPPED;
	}

	@SuppressWarnings("deprecation")
	public boolean isOpaqueCube(IBlockState state)
	{
		return false;
	}

	@SideOnly(Side.CLIENT)
	@SuppressWarnings("deprecation")
	public boolean shouldSideBeRendered(IBlockState blockState, @Nonnull IBlockAccess blockAccess, @Nonnull BlockPos pos, EnumFacing side)
	{
		return true;
	}
}
