package net.xalcon.ecotec.common.multiblock;

import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.xalcon.ecotec.Ecotec;
import net.xalcon.ecotec.common.blocks.logistics.BlockFluidTank;
import net.xalcon.ecotec.common.init.ModBlocks;
import org.apache.logging.log4j.Marker;
import org.apache.logging.log4j.MarkerManager;

public class RectangularMultiblockHelper
{
	public static void scanForMultiblock(World world, BlockPos pos)
	{
		BlockPos topEdge = null;
		BlockPos botEdge = null;
		BlockPos eastEdge = null;
		BlockPos westEdge = null;
		BlockPos northEdge = null;
		BlockPos southEdge = null;

		topEdge = findFurthest(world, pos, EnumFacing.UP, 16);
		if(topEdge != null)
			botEdge = findFurthest(world, pos, EnumFacing.DOWN, 16);

		eastEdge = findFurthest(world, pos, EnumFacing.EAST, 16);
		if(eastEdge != null)
			westEdge = findFurthest(world, pos, EnumFacing.WEST, 16);

		northEdge = findFurthest(world, pos, EnumFacing.NORTH, 16);
		if(northEdge != null)
			southEdge = findFurthest(world, pos, EnumFacing.SOUTH, 16);

		if(topEdge == null && eastEdge != null)
		{
			topEdge = findFurthest(world, eastEdge, EnumFacing.UP, 16);
			botEdge = findFurthest(world, eastEdge, EnumFacing.DOWN, 16);
		}
		if(eastEdge == null && topEdge != null)
		{
			eastEdge = findFurthest(world, topEdge, EnumFacing.EAST, 16);
			westEdge = findFurthest(world, topEdge, EnumFacing.WEST, 16);
		}
		if(northEdge == null && eastEdge != null)
		{
			northEdge = findFurthest(world, eastEdge, EnumFacing.NORTH, 16);
			southEdge = findFurthest(world, eastEdge, EnumFacing.SOUTH, 16);
		}

		Ecotec.Log.info("Top @ " + topEdge);
		Ecotec.Log.info("Bot @ " + botEdge);
		Ecotec.Log.info("east @ " + eastEdge);
		Ecotec.Log.info("west @ " + westEdge);
		Ecotec.Log.info("north @ " + northEdge);
		Ecotec.Log.info("south @ " + southEdge);

		if(eastEdge == null || westEdge == null || northEdge == null || southEdge == null || topEdge == null || botEdge == null)
			return;

		int minX = Math.min(westEdge.getX(), eastEdge.getX());
		int maxX = Math.max(westEdge.getX(), eastEdge.getX());
		int minY = Math.min(topEdge.getY(), botEdge.getY());
		int maxY = Math.max(topEdge.getY(), botEdge.getY());
		int minZ = Math.min(northEdge.getZ(), southEdge.getZ());
		int maxZ = Math.max(northEdge.getZ(), southEdge.getZ());

		Ecotec.Log.info("[" + minX + "/" + minY + "/" + minZ + "] -> [" + maxX + "/" + maxY + "/" + maxZ + "]");

		for(int x = minX; x <= maxX; x++)
		{
			for(int y = minY; y <= maxY; y++)
			{
				for(int z = minZ; z <= maxZ; z++)
				{

					//world.setBlockState(new BlockPos(x, y, z), Blocks.STONE.getDefaultState(), 3);
				}
			}
		}
	}

	private static BlockPos findFurthest(World world, BlockPos start, EnumFacing direction, int maxDistance)
	{
		BlockPos lastValidEdgeBlock = null;
		for (int i = 0; i < maxDistance; i++)
		{
			BlockPos checkPos = start.offset(direction, i);
			IBlockState blockState = world.getBlockState(checkPos);
			if (blockState.getBlock() != ModBlocks.FluidTank)
				break;
			if (blockState.getValue(BlockFluidTank.TANK_BLOCK_TYPE).isEdgeBlock())
			{
				lastValidEdgeBlock = checkPos;
				// TODO: check if the other planes than the lookup plane would make this block an valid edge
				// This way we can could support side-by-side tanks without a non-tank layer in between
			}
		}
		return lastValidEdgeBlock;
	}
}
