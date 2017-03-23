package net.xalcon.sirenity.common.tileentities;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityAgeable;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.storage.WorldInfo;
import net.xalcon.sirenity.common.blocks.BlockMachineBase;
import net.xalcon.sirenity.common.util.BlockMath;

public class TileEntityMachineChronotyper extends TileEntity implements ITickable
{
	private int radius = 1;

	public AxisAlignedBB workBounds;

	@Override
	public void update()
	{
		EnumFacing facing = this.getWorld().getBlockState(this.getPos()).getValue(BlockMachineBase.FACING);

		int diameter = radius * 2 + 1;
		BlockMath.RelativePosition relPosTL = BlockMath.rotatePosition(-radius, -diameter, facing);
		BlockMath.RelativePosition relPosBR = BlockMath.rotatePosition(radius, -1, facing);

		BlockPos pos1 = new BlockPos(this.getPos().getX() + relPosTL.x, this.getPos().getY(), this.getPos().getZ() + relPosTL.z);
		BlockPos pos2 = new BlockPos(this.getPos().getX() + relPosBR.x, this.getPos().getY(), this.getPos().getZ() + relPosBR.z);
		AxisAlignedBB area = new AxisAlignedBB(pos1, pos2);
		area = new AxisAlignedBB(area.minX, area.minY, area.minZ, area.maxX + 1, area.maxY + 1, area.maxZ + 1);
		workBounds = area;
		for(Entity entity : this.getWorld().getEntitiesWithinAABB(Entity.class, area))
		{
			System.out.println(entity.getName());
		}
	}
}
