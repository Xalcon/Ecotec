package net.xalcon.ecotec.common.farmables.harvestable;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.xalcon.ecotec.api.EnumHarvestType;
import net.xalcon.ecotec.api.IEcotecHarvestable;

import java.util.List;

/**
 * Used for plants that just need to be broken
 * i.e.: all vanilla except aging plants
 */
public class HarvestableBasic implements IEcotecHarvestable
{
	private Block harvestBlock;
	private EnumHarvestType harvestType;

	public HarvestableBasic(Block harvestBlock)
	{
		this(harvestBlock, EnumHarvestType.Normal);
	}

	public HarvestableBasic(Block harvestBlock, EnumHarvestType harvestType)
	{
		this.harvestBlock = harvestBlock;
		this.harvestType = harvestType;
	}

	@Override
	public Block getPlantedBlock()
	{
		return this.harvestBlock;
	}

	@Override
	public EnumHarvestType getHarvestType()
	{
		return this.harvestType;
	}

	@Override
	public boolean canBeHarvested(World world, BlockPos plantPos, IBlockState state)
	{
		return true;
	}

	@Override
	public List<ItemStack> getDrops(World world, BlockPos pos, IBlockState state)
	{
		return this.harvestBlock.getDrops(world, pos, state, 0);
	}
}
