package net.xalcon.ecotec.api.components;

import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.xalcon.ecotec.core.IEcotecComponent;

public interface IWorldInteractive extends IEcotecComponent
{
	/**
	 * gets the radius for this world interactable
	 * @return radius in blocks excluding the center block
	 */
	int getRadius();

	/**
	 * gets the working area from a position
	 * @param pos the origin point of the area
	 * @param direction the facing direction of the world interactable
	 * @return the work area
	 */
	AxisAlignedBB getArea(BlockPos pos, EnumFacing direction);
}
