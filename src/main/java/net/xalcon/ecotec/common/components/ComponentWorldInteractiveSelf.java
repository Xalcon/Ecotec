package net.xalcon.ecotec.common.components;

import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;

import javax.annotation.Nullable;

public class ComponentWorldInteractiveSelf extends ComponentWorldInteractiveFrontal
{
	public ComponentWorldInteractiveSelf(int radius)
	{
		super(radius);
	}

	@Override
	public AxisAlignedBB getArea(BlockPos pos, @Nullable EnumFacing direction)
	{
		return new AxisAlignedBB(pos).expand(this.getRadius(), 1, this.getRadius());
	}
}
