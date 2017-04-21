package net.xalcon.ecotec.common.tileentities.machines.rancherlogic;

import net.minecraft.entity.Entity;
import net.xalcon.ecotec.common.tileentities.agriculture.TileEntityMachineRancher;

public interface IEntityRancherLogic
{
	boolean ranchEntity(TileEntityMachineRancher tileEntity, Entity entity);
}
