package net.xalcon.ecotec.integration.vanilla.rancher;

import net.minecraft.entity.Entity;
import net.minecraft.entity.passive.EntityMooshroom;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidStack;
import net.xalcon.ecotec.api.IEntityRancherLogic;
import net.xalcon.ecotec.common.init.ModFluids;
import net.xalcon.ecotec.common.tileentities.agriculture.TileEntityMachineRancher;

public class EntityRancherMooshroomLogic implements IEntityRancherLogic
{
	private int milkCooldownTicks = 20 * 30; // TODO: Add configuration option
	private int milkAmount = Fluid.BUCKET_VOLUME;

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
				entityData.setLong("eco:msoup_cd", totalWorldTime + milkCooldownTicks);
				FluidStack stack = new FluidStack(ModFluids.FluidMushroomSoup, milkAmount);
				tileEntity.getMushroomSoupTank().fill(stack, true);
				return true;
			}
		}
		return false;
	}
}
