package net.xalcon.ecotec.common.farmables.harvestable;

import net.minecraft.block.state.IBlockState;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.xalcon.ecotec.EcotecRegistries;
import net.xalcon.ecotec.api.EnumHarvestType;
import net.xalcon.ecotec.api.IEcotecHarvestable;

import java.util.HashSet;
import java.util.LinkedList;

public class TreeHarvestManager
{
	private World world;

	private final LinkedList<BlockPos> openSet = new LinkedList<>();
	private final HashSet<BlockPos> closedSet = new HashSet<>();

	public TreeHarvestManager(World world)
	{
		this.world = world;
	}

	public boolean isDone() { return this.openSet.size() == 0; }

	public BlockPos getNext()
	{
		if(this.isDone()) return null;
		BlockPos pos = this.openSet.pollFirst();
		this.closedSet.add(pos);
		this.discoverBlocks(pos);
		return pos;
	}

	public void reset(BlockPos pos)
	{
		this.closedSet.clear();
		this.openSet.clear();
		this.openSet.add(pos);
	}

	private void discoverBlocks(BlockPos pos)
	{
		for(EnumFacing side : EnumFacing.VALUES)
		{
			BlockPos discoverBlockPos = pos.offset(side);
			if(this.closedSet.contains(discoverBlockPos) || this.openSet.contains(discoverBlockPos)) continue;

			IBlockState blockState = this.world.getBlockState(discoverBlockPos);
			IEcotecHarvestable harvestable = EcotecRegistries.Harvestables.find(blockState.getBlock());
			if(harvestable == null) continue;

			if(harvestable.canBeHarvested(this.world, discoverBlockPos, blockState))
			{
				EnumHarvestType harvestType = harvestable.getHarvestType();
				if(harvestType == EnumHarvestType.Tree)
				{
					this.openSet.addLast(discoverBlockPos);
				}
				else if(harvestType == EnumHarvestType.TreeLeaves)
				{
					this.openSet.addFirst(discoverBlockPos);
				}
			}
		}
	}
}
