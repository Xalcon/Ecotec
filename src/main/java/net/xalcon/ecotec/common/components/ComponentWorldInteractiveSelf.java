package net.xalcon.ecotec.common.components;

import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;

public class ComponentWorldInteractiveSelf extends ComponentWorldInteractiveFrontal
{
	private int verticalRadius;
	private int verticalOffset;

	public ComponentWorldInteractiveSelf(int radius, int verticalRadius, int verticalOffset)
	{
		super(radius);
		this.verticalRadius = verticalRadius;
		this.verticalOffset = verticalOffset;
	}

	@Override
	public AxisAlignedBB getArea()
	{
		if(this.loc == null)
			return new AxisAlignedBB(0, 0, 0, 0, 0, 0);
		BlockPos pos = this.loc.getPos();
		int r = this.getRadius();
		return new AxisAlignedBB(pos.up(this.verticalOffset)).expand(r, this.verticalRadius, r);
	}
}
