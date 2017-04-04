package net.xalcon.minefactory.common.tileentities.machines.rancherlogic;

import net.minecraft.entity.Entity;
import net.xalcon.minefactory.common.tileentities.machines.TileEntityMachineRancher;

public interface IEntityRancherLogic
{
	boolean ranchEntity(TileEntityMachineRancher tileEntity, Entity entity);
}