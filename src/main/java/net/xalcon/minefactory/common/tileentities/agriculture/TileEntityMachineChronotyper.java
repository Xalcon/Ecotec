package net.xalcon.minefactory.common.tileentities.agriculture;

import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraft.util.math.AxisAlignedBB;
import net.xalcon.minefactory.common.blocks.BlockMachineBase;
import net.xalcon.minefactory.common.tileentities.TileEntityMachineWorldInteractive;

public class TileEntityMachineChronotyper extends TileEntityMachineWorldInteractive implements ITickable
{
	public TileEntityMachineChronotyper()
	{
		super(0);
	}

	@Override
	public int getMaxIdleTicks()
	{
		return 100;
	}

	@Override
	public int getMaxProgressTicks()
	{
		return 1;
	}

	@Override
	protected boolean doWork()
	{
		int radius = this.getWorkRadius();
		EnumFacing facing = this.getWorld().getBlockState(this.getPos()).getValue(BlockMachineBase.FACING);
		AxisAlignedBB area = new AxisAlignedBB(this.getPos().offset(facing, radius + 1)).expand(radius, 0, radius);
		for (EntityAnimal entity : this.getWorld().getEntitiesWithinAABB(EntityAnimal.class, area))
		{
			if (entity.isChild())
			{
				entity.moveToBlockPosAndAngles(this.getPos().offset(facing.getOpposite()), entity.rotationYaw, entity.rotationPitch);
				this.setIdleTicks(10);
				return true;
			}
		}
		return false;
	}

	@Override
	public String getUnlocalizedName()
	{
		return "machine_chronotyper";
	}
}
