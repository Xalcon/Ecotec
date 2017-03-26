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

public class TileEntityMachineChronotyper extends TileEntityMachineBase implements ITickable
{
	private int radius = 2;

	public TileEntityMachineChronotyper()
	{
		super(0);
	}

	@Override
	public void update()
	{
		EnumFacing facing = this.getWorld().getBlockState(this.getPos()).getValue(BlockMachineBase.FACING);
		AxisAlignedBB area = new AxisAlignedBB(this.getPos().offset(facing, radius + 1)).expand(radius, 0, radius);
		for(Entity entity : this.getWorld().getEntitiesWithinAABB(Entity.class, area))
		{
			entity.moveToBlockPosAndAngles(this.getPos().offset(facing.getOpposite()), entity.rotationYaw, entity.rotationPitch);
		}
	}

	@Override
	public String getName()
	{
		return "machine_chronotyper";
	}
}
