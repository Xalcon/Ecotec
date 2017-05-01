package net.xalcon.ecotec.common.components;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.xalcon.ecotec.api.components.IWorldInteractive;
import net.xalcon.ecotec.common.tileentities.NbtSyncType;
import net.xalcon.ecotec.core.IEcotecComponent;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class ComponentWorldInteractiveFrontal implements IWorldInteractive
{
	private int radius;

	public ComponentWorldInteractiveFrontal(int radius)
	{
		this.radius = radius;
	}

	@Override
	public int getRadius()
	{
		return this.radius;
	}

	@Override
	public AxisAlignedBB getArea(BlockPos pos, @Nullable EnumFacing direction)
	{
		return new AxisAlignedBB(pos.offset(direction == null ? EnumFacing.SOUTH : direction, this.radius + 1)).expand(this.radius, 0, this.radius);
	}

	@Override
	public void readSyncNbt(@Nonnull NBTTagCompound nbt, @Nonnull NbtSyncType type) { }

	@Override
	public void writeSyncNbt(@Nonnull NBTTagCompound nbt, @Nonnull NbtSyncType type) { }
}
