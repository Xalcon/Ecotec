package net.xalcon.minefactory.common.tileentities.machines;

import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraft.util.math.AxisAlignedBB;
import net.xalcon.minefactory.common.blocks.BlockMachineBase;
import net.xalcon.minefactory.common.tileentities.TileEntityMachine;

public class TileEntityMachineChronotyper extends TileEntityMachine implements ITickable
{
	public TileEntityMachineChronotyper()
	{
		super(0);
	}

	@Override
	public void update()
	{
		if(this.getWorld().isRemote) return;
		int radius = this.getWorkRadius();
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
