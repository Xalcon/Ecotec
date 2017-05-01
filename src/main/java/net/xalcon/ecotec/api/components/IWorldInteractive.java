package net.xalcon.ecotec.api.components;

import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;

public interface IWorldInteractive extends IEcotecComponent<IWorldInteractive>
{
	/**
	 * gets the radius for this world interactable
	 * @return radius in blocks excluding the center block
	 */
	int getRadius();

	/**
	 * gets the working area
	 * @return the work area
	 */
	AxisAlignedBB getArea();

	/**
	 * can be used to iterate over all blocks in this volume.
	 */
	BlockPos getBlockFromIndex(int i);

	/**
	 * returns the amount of blocks in this volume
	 * @return amount of blocks
	 */
	int getBlockCount();
}
