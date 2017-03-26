package net.xalcon.minefactory.common.tileentities.machines;

import net.minecraft.entity.Entity;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraft.util.math.AxisAlignedBB;
import net.xalcon.minefactory.common.blocks.BlockMachineBase;
import net.xalcon.minefactory.common.tileentities.TileEntityMachineBase;

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
		for(EntityAnimal entity : this.getWorld().getEntitiesWithinAABB(EntityAnimal.class, area))
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
