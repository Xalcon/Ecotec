package net.xalcon.ecotec.common.components;

import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.xalcon.ecotec.Ecotec;
import net.xalcon.ecotec.api.components.IBlockLocation;
import net.xalcon.ecotec.api.components.IWorldInteractive;
import net.xalcon.ecotec.common.init.ModCaps;

public class ComponentWorldInteractiveFrontal implements IWorldInteractive
{
	private int radius;
	IBlockLocation loc;

	public ComponentWorldInteractiveFrontal()
	{
		this(1);
	}

	public ComponentWorldInteractiveFrontal(int radius)
	{
		this.radius = radius;
	}

	@Override
	public void initialize(ICapabilityProvider provider)
	{
		this.loc = provider.getCapability(ModCaps.getBlockLocationCap(), null);
	}

	@Override
	public void invalidate()
	{
		this.loc = null;
	}

	@Override
	public Capability<IWorldInteractive> getCapability()
	{
		return ModCaps.getWorldInteractiveCap();
	}

	@Override
	public int getRadius()
	{
		return this.radius;
	}

	@Override
	public AxisAlignedBB getArea()
	{
		if(this.loc == null)
			return new AxisAlignedBB(0, 0, 0, 0, 0, 0);
		BlockPos pos = this.loc.getPos();
		EnumFacing direction = this.loc.getBlockFacing();
		if(direction == null)
			Ecotec.Log.error("block facing for block @ " + pos + " is null, this is bad!");
		return new AxisAlignedBB(pos.offset(direction == null ? EnumFacing.SOUTH : direction, this.radius + 1)).expand(this.radius, 0, this.radius);
	}

	@Override
	public BlockPos getBlockFromIndex(int i)
	{
		return null;
	}

	@Override
	public int getBlockCount()
	{
		return 0;
	}
}
