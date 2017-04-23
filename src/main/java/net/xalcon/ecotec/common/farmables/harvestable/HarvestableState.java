package net.xalcon.ecotec.common.farmables.harvestable;

import net.minecraft.block.Block;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class HarvestableState<T extends Comparable<T>> extends HarvestableBasic
{
	private final IProperty<T> prop;
	private final T targetValue;

	public HarvestableState(Block harvestBlock, IProperty<T> prop, T targetValue)
	{
		super(harvestBlock);
		this.prop = prop;
		this.targetValue = targetValue;
	}

	@Override
	public boolean canBeHarvested(World world, BlockPos plantPos, IBlockState state)
	{
		return state.getValue(this.prop) == this.targetValue;
	}
}
