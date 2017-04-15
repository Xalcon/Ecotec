package net.xalcon.minefactory.common.tileentities.machines.rancherlogic;

import net.minecraft.entity.Entity;
import net.minecraft.entity.passive.EntitySheep;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.xalcon.minefactory.common.tileentities.agriculture.TileEntityMachineRancher;

import java.util.Random;

public class EntityRancherSheepLogic implements IEntityRancherLogic
{
	private final Random rand = new Random();

	@Override
	public boolean ranchEntity(TileEntityMachineRancher tileEntity, Entity entity)
	{
		if(entity instanceof EntitySheep)
		{
			EntitySheep sheep = (EntitySheep) entity;
			if(!sheep.getSheared() && !sheep.isChild())
			{
				int i = 1 + this.rand.nextInt(3);
				sheep.setSheared(true);
				ItemStack itemStack = new ItemStack(Item.getItemFromBlock(Blocks.WOOL), i, sheep.getFleeceColor().getMetadata());
				tileEntity.insertItemStack(itemStack);
				return true;
			}
		}
		return false;
	}
}
