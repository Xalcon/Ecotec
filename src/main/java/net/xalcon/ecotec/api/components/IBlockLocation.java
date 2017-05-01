package net.xalcon.ecotec.api.components;

import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public interface IBlockLocation extends IEcotecComponent
{
	/**
	 * the world of this thing
	 * @return world
	 */
	World getWorld();

	/**
	 * the position in the world
	 * @return positon
	 */
	BlockPos getPos();

	/**
	 * Returns the block facing or null if this block has no facing
	 * @return the facing of the block
	 */
	EnumFacing getBlockFacing();
}
