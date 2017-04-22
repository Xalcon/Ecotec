package net.xalcon.ecotec.common.util;

import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;

public class IterativeAreaWalker
{
	private BlockPos topLeft;
	private int currentIndex;
	private int maxIndex;
	private int deltaX;
	private int deltaY;
	private int deltaZ;

	public IterativeAreaWalker(AxisAlignedBB bounds)
	{
		this(new BlockPos(bounds.minX, bounds.maxY - 1, bounds.minZ), new BlockPos(bounds.maxX, bounds.minY - 1, bounds.maxZ));
	}

	public IterativeAreaWalker(BlockPos pos1, BlockPos pos2)
	{
		this.topLeft = new BlockPos(
				Math.min(pos1.getX(), pos2.getX()),
				Math.max(pos1.getY(), pos2.getY()),
				Math.min(pos1.getZ(), pos2.getZ()));


		BlockPos bottomRight = new BlockPos(
				Math.max(pos1.getX(), pos2.getX()),
				Math.min(pos1.getY(), pos2.getY()),
				Math.max(pos1.getZ(), pos2.getZ()));

		this.deltaX = Math.abs(topLeft.getX() - bottomRight.getX());
		this.deltaY = Math.abs(topLeft.getY() - bottomRight.getY());
		this.deltaZ = Math.abs(topLeft.getZ() - bottomRight.getZ());
		this.maxIndex = this.deltaX * this.deltaY * this.deltaZ;
	}

	public void reset()
	{
		this.currentIndex = 0;
	}

	public BlockPos getNext()
	{
		int x = this.currentIndex % this.deltaX;
		int y = (this.currentIndex % (this.deltaX * deltaZ)) / this.deltaZ;
		int z = this.currentIndex / (this.deltaX * deltaZ);
		this.currentIndex = ++this.currentIndex % this.maxIndex;
		return this.topLeft.east(x).south(y).down(z);
	}
}
