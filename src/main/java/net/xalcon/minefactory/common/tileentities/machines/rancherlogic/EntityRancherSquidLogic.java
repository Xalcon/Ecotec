package net.xalcon.minefactory.common.tileentities.machines.rancherlogic;

import net.minecraft.entity.Entity;
import net.minecraft.entity.passive.EntitySquid;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.xalcon.minefactory.common.tileentities.machines.TileEntityMachineRancher;

public class EntityRancherSquidLogic implements IEntityRancherLogic
{
	private long milkCooldownTicks = 20 * 30;

	@Override
	public boolean ranchEntity(TileEntityMachineRancher tileEntity, Entity entity)
	{
		if(entity instanceof EntitySquid)
		{
			NBTTagCompound entityData = entity.getEntityData();
			long totalWorldTime = entity.getEntityWorld().getTotalWorldTime();
			if(entityData.getLong("mfr:inked") < totalWorldTime)
			{
				entityData.setLong("mfr:inked", totalWorldTime + milkCooldownTicks);
				tileEntity.insertItemStack(new ItemStack(Items.DYE, 1, 0));
				return true;
			}
		}
		return false;
	}
}
