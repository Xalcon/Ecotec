package net.xalcon.minefactory.common.tileentities.machines.rancherlogic;

import net.minecraft.entity.Entity;
import net.minecraft.entity.passive.EntityCow;
import net.minecraft.entity.passive.EntityMooshroom;
import net.minecraft.entity.passive.EntitySheep;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidStack;
import net.xalcon.minefactory.common.init.ModBlocks;
import net.xalcon.minefactory.common.init.ModFluids;
import net.xalcon.minefactory.common.tileentities.machines.TileEntityMachineRancher;

import java.util.Random;

public class EntityRancherCowLogic implements IEntityRancherLogic
{
	private int milkCooldownTicks = 20 * 30; // TODO: Add configuration option
	private int milkAmount = Fluid.BUCKET_VOLUME;

	@Override
	public boolean ranchEntity(TileEntityMachineRancher tileEntity, Entity entity)
	{
		if(entity instanceof EntityCow)
		{
			EntityCow cow = (EntityCow) entity;
			NBTTagCompound entityData = cow.getEntityData();
			long totalWorldTime = entity.getEntityWorld().getTotalWorldTime();
			if(!cow.isChild() && entityData.getLong("mfr:milk_cd") < totalWorldTime)
			{
				entityData.setLong("mfr:milk_cd", totalWorldTime + milkCooldownTicks);
				FluidStack stack = new FluidStack(ModFluids.FluidMilk, milkAmount);
				tileEntity.getMilkTank().fill(stack, true);
				return true;
			}
		}
		return false;
	}
}
