package net.xalcon.ecotec.integration.vanilla.rancher;

import net.minecraft.entity.Entity;
import net.minecraft.entity.passive.EntitySquid;
import net.minecraft.init.Items;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemHandlerHelper;
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
				ItemStack itemStack = new ItemStack(Items.DYE, VanillaCompat.getConfig().getRancherConfig().getRanchSquidsAmount(), EnumDyeColor.BLACK.getMetadata());
				IItemHandler itemHandler = tileEntity.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, null);
				itemStack = ItemHandlerHelper.insertItemStacked(itemHandler, itemStack, false);
				return itemStack.isEmpty();
			}
		}
		return false;
	}
}
