package net.xalcon.sirenity.common.util;

import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;

public class BlockMath
{
	public static class RelativePosition
	{
		public int x;
		public int z;

		public RelativePosition(int x, int z)
		{
			this.x = x;
			this.z = z;
		}
	}

	public static RelativePosition rotatePosition(int x, int z, EnumFacing facing)
	{
		switch(facing)
		{
			default:
			case NORTH:
				return new RelativePosition(x, z);
			case SOUTH:
				return new RelativePosition(-x, -z);
			case WEST:
				return new RelativePosition(z, -x);
			case EAST:
				return new RelativePosition(-z, x);
		}
	}
}
