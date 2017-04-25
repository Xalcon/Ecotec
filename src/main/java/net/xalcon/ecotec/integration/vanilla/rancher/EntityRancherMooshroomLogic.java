package net.xalcon.ecotec.integration.vanilla.rancher;

import net.minecraft.entity.Entity;
import net.minecraft.entity.passive.EntityMooshroom;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.fluids.FluidStack;
import net.xalcon.ecotec.api.IEntityRancherLogic;
import net.xalcon.ecotec.common.init.ModFluids;
import net.xalcon.ecotec.common.tileentities.agriculture.TileEntityMachineRancher;
import net.xalcon.ecotec.integration.vanilla.VanillaCompat;

public class EntityRancherMooshroomLogic implements IEntityRancherLogic
{
	@Override
	public boolean ranchEntity(TileEntityMachineRancher tileEntity, Entity entity)
	{
		if (entity instanceof EntityMooshroom)
		{
			EntityMooshroom cow = (EntityMooshroom) entity;
			NBTTagCompound entityData = cow.getEntityData();
			long totalWorldTime = entity.getEntityWorld().getTotalWorldTime();
			if (!cow.isChild() && entityData.getLong("eco:msoup_cd") < totalWorldTime)
			{
				entityData.setLong("eco:msoup_cd", totalWorldTime + VanillaCompat.getConfig().getRancherConfig().getRanchMooshroomsCooldown());
				FluidStack stack = new FluidStack(ModFluids.FluidMushroomSoup, VanillaCompat.getConfig().getRancherConfig().getRanchMooshroomsAmount());
				tileEntity.getMushroomSoupTank().fill(stack, true);
				return true;
			}
		}
		return false;
	}
}
