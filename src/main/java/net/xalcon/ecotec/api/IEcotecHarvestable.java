package net.xalcon.ecotec.api;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.List;

public interface IEcotecHarvestable
{
	/**
	 * The block that is handled by this harvestable
	 * @return the harvestable block
	 */
	Block getPlantedBlock();

	/**
	 * Should return the way this block is supposed to be harvested
	 * @return the harvest type
	 */
	EnumHarvestType getHarvestType();

	/**
	 * Should return if the block is ready to be harvested
	 * @param world the world
	 * @param plantPos the position the block is at
	 * @param state the current block state
	 * @return True if the block can be harvested, otherwise false
	 */
	boolean canBeHarvested(World world, BlockPos plantPos, IBlockState state);

	/**
	 * Should return the drops for this harvest
	 * @param world the world
	 * @param pos the position the block is at
	 * @param state the current block state
	 * @return list of drops
	 */
	List<ItemStack> getDrops(World world, BlockPos pos, IBlockState state);

	/**
	 * will be called to harvest the block.
	 * Should set the block to the proper block state (like air for vanilla crops, etc)
	 * @param world the world
	 * @param pos the position the block is at
	 * @param state the current block statee
	 */
	default void harvestBlock(World world, BlockPos pos, IBlockState state)
	{
		world.setBlockToAir(pos);
	}
}
