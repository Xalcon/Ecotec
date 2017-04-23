package net.xalcon.ecotec.common.farmables.plantables;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import javax.annotation.Nonnull;

public class PlantableCrop extends PlantableBasic
{
	public PlantableCrop(Block plantBlock, Item plantItem)
	{
		super(plantBlock, plantItem);
	}

	@Override
	public boolean canBePlantedAt(@Nonnull World world, @Nonnull BlockPos pos, @Nonnull ItemStack itemStack)
	{
		if(!world.isAirBlock(pos))
			return false;

		Block groundBlock = world.getBlockState(pos.down()).getBlock();
		// TODO: Add support for modded soils
		return groundBlock == Blocks.FARMLAND ||
				groundBlock == Blocks.GRASS ||
				groundBlock == Blocks.DIRT ||
				super.canBePlantedAt(world, pos, itemStack);
	}

	@Override
	public void onPlanting(World world, BlockPos pos, ItemStack itemStack, IBlockState blockState)
	{
		IBlockState soilBlock = world.getBlockState(pos.down());
		Block block = soilBlock.getBlock();
		if(block == Blocks.DIRT || block == Blocks.GRASS)
			world.setBlockState(pos.down(), Blocks.FARMLAND.getDefaultState());
	}
}
