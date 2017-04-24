package net.xalcon.ecotec.common.world.gen;

import net.minecraft.block.BlockLog;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.chunk.IChunkGenerator;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.gen.feature.WorldGenTrees;
import net.minecraftforge.fml.common.IWorldGenerator;
import net.xalcon.ecotec.common.blocks.world.BlockRubberTreeLog;
import net.xalcon.ecotec.common.init.ModBlocks;

import java.util.Random;

public class WorldGenRubberTreeSmall extends WorldGenTrees implements IWorldGenerator
{
	public WorldGenRubberTreeSmall()
	{
		super(true, 8, ModBlocks.RubberTreeLog.getDefaultState().withProperty(BlockLog.LOG_AXIS, BlockLog.EnumAxis.Y), ModBlocks.RubberTreeLeaves.getDefaultState(), false);
	}

	@Override
	public void generate(Random random, int chunkX, int chunkZ, World world, IChunkGenerator chunkGenerator, IChunkProvider chunkProvider)
	{
		this.generate(world, random, new BlockPos((chunkX << 4) + 8, 64, (chunkZ << 4) + 8));
	}
}
