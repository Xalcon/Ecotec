package net.xalcon.ecotec.integration.vanilla.rancher;

import net.minecraft.entity.Entity;
import net.minecraft.entity.passive.EntitySquid;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.xalcon.ecotec.api.IEntityRancherLogic;
import net.xalcon.ecotec.common.tileentities.agriculture.TileEntityMachineRancher;

public class EntityRancherSquidLogic implements IEntityRancherLogic
{
	private long milkCooldownTicks = 20 * 30;

	@Override
	public boolean ranchEntity(TileEntityMachineRancher tileEntity, Entity entity)
	{
		if (entity instanceof EntitySquid)
		{
			NBTTagCompound entityData = entity.getEntityData();
			long totalWorldTime = entity.getEntityWorld().getTotalWorldTime();
			if (entityData.getLong("eco:inked") < totalWorldTime)
			{
				entityData.setLong("eco:inked", totalWorldTime + milkCooldownTicks);
				tileEntity.insertItemStack(new ItemStack(Items.DYE, 1, 0));
				return true;
			}
		}
		return false;
	}
}
