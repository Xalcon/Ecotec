package net.xalcon.ecotec.client.renderer.block;

import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.color.IBlockColor;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.xalcon.ecotec.common.blocks.IBlockTintable;

import javax.annotation.Nullable;

public class BlockTintColorHandler implements IBlockColor
{
	public final static BlockTintColorHandler Instance = new BlockTintColorHandler();

	@Override
	public int colorMultiplier(IBlockState state, @Nullable IBlockAccess worldIn, @Nullable BlockPos pos, int tintIndex)
	{
		return ((IBlockTintable) state.getBlock()).getColorTint(state, worldIn, pos, tintIndex);
	}
}
