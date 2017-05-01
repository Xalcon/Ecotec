package net.xalcon.ecotec.common.tileentities.agriculture;

import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.AxisAlignedBB;
import net.xalcon.ecotec.common.components.ComponentEnergyStorage;
import net.xalcon.ecotec.common.components.ComponentWorldInteractiveFrontal;
import net.xalcon.ecotec.common.tileentities.TileEntityTickable;

public class TileEntityMachineChronotyper extends TileEntityTickable
{
	private final ComponentWorldInteractiveFrontal worldInteractive;
	//private final ComponentEnergyStorage energyStorage;

	public TileEntityMachineChronotyper()
	{
		this.worldInteractive = this.addComponent(new ComponentWorldInteractiveFrontal(1));
		/*this.energyStorage = */this.addComponent(new ComponentEnergyStorage(512, 0, 16000));
	}

	@Override
	protected boolean doWork()
	{
		AxisAlignedBB area = this.worldInteractive.getArea();
		for (EntityAnimal entity : this.getWorld().getEntitiesWithinAABB(EntityAnimal.class, area))
		{
			if (entity.isChild())
			{
				EnumFacing facing = this.blockLocation.getBlockFacing();
				entity.moveToBlockPosAndAngles(this.getPos().offset(facing.getOpposite()), entity.rotationYaw, entity.rotationPitch);
				this.setIdleTime((short)5);
				return true;
			}
		}
		return false;
	}
}
