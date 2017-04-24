package net.xalcon.ecotec.integration.vanilla.rancher;

import net.minecraft.entity.Entity;
import net.minecraft.entity.passive.EntityCow;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidStack;
import net.xalcon.ecotec.api.IEntityRancherLogic;
import net.xalcon.ecotec.common.init.ModFluids;
import net.xalcon.ecotec.common.tileentities.agriculture.TileEntityMachineRancher;
import net.xalcon.ecotec.integration.vanilla.VanillaCompat;

public class EntityRancherCowLogic implements IEntityRancherLogic
{
	@Override
	public boolean ranchEntity(TileEntityMachineRancher tileEntity, Entity entity)
	{
		if (entity instanceof EntityCow)
		{
			EntityCow cow = (EntityCow) entity;
			NBTTagCompound entityData = cow.getEntityData();
			long totalWorldTime = entity.getEntityWorld().getTotalWorldTime();
			if (!cow.isChild() && entityData.getLong("eco:milk_cd") < totalWorldTime)
			{
				entityData.setLong("eco:milk_cd", totalWorldTime + VanillaCompat.getConfig().getRancherConfig().getRanchCowsCooldown());
				FluidStack stack = new FluidStack(ModFluids.FluidMilk, VanillaCompat.getConfig().getRancherConfig().getRanchCowsAmount());
				tileEntity.getMilkTank().fill(stack, true);
				return true;
			}
		}
		return false;
	}
}
