package net.xalcon.ecotec.common.farmables.harvestable;

import net.minecraft.block.Block;
import net.minecraft.block.BlockCrops;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.xalcon.ecotec.api.EnumHarvestType;

public class HarvestableAgingCrop extends HarvestableBasic
{
	public HarvestableAgingCrop(BlockCrops harvestBlock)
	{
		super(harvestBlock);
	}

	public HarvestableAgingCrop(BlockCrops harvestBlock, EnumHarvestType harvestType)
	{
		super(harvestBlock, harvestType);
	}

	@Override
	public boolean canBeHarvested(World world, BlockPos plantPos, IBlockState state)
	{
		return ((BlockCrops)this.getPlantedBlock()).isMaxAge(state);
	}
}
