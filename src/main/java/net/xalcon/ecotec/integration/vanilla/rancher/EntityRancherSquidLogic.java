package net.xalcon.ecotec.integration.vanilla.rancher;

import net.minecraft.entity.Entity;
import net.minecraft.entity.passive.EntitySquid;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.xalcon.ecotec.api.IEntityRancherLogic;
import net.xalcon.ecotec.common.tileentities.agriculture.TileEntityMachineRancher;
import net.xalcon.ecotec.integration.vanilla.VanillaCompat;

public class EntityRancherSquidLogic implements IEntityRancherLogic
{
	@Override
	public boolean ranchEntity(TileEntityMachineRancher tileEntity, Entity entity)
	{
		if (entity instanceof EntitySquid)
		{
			NBTTagCompound entityData = entity.getEntityData();
			long totalWorldTime = entity.getEntityWorld().getTotalWorldTime();
			if (entityData.getLong("eco:inked") < totalWorldTime)
			{
				entityData.setLong("eco:inked", totalWorldTime + VanillaCompat.getConfig().getRancherConfig().getRanchSquidsCooldown());
				tileEntity.insertItemStack(new ItemStack(Items.DYE, VanillaCompat.getConfig().getRancherConfig().getRanchSquidsAmount(), 0));
				return true;
			}
		}
		return false;
	}
}
