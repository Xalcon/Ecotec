package net.xalcon.ecotec.common.tileentities.machines;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EnumCreatureType;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.AxisAlignedBB;
import net.xalcon.ecotec.common.components.ComponentEnergyStorage;
import net.xalcon.ecotec.common.components.ComponentItemHandler;
import net.xalcon.ecotec.common.components.ComponentWorldInteractiveFrontal;
import net.xalcon.ecotec.common.components.ComponentWorldInteractiveSelf;
import net.xalcon.ecotec.common.init.ModItems;
import net.xalcon.ecotec.common.inventories.guiprovider.GuiProviderAutoSpawner;
import net.xalcon.ecotec.common.inventories.guiprovider.GuiProviderDeepStorageUnit;
import net.xalcon.ecotec.common.items.ItemSafariNet;
import net.xalcon.ecotec.common.tileentities.TileEntityTickable;

public class TileEntityMachineAutoSpawner extends TileEntityTickable
{
	private final ComponentItemHandler inventory;
	private final ComponentWorldInteractiveFrontal worldInteractive;
	//private final ComponentEnergyStorage energyStorage;

	public TileEntityMachineAutoSpawner()
	{
		this.inventory = this.addComponent(new ComponentItemHandler(1));
		this.worldInteractive = this.addComponent(new ComponentWorldInteractiveSelf(1, 1, 0));
		/*this.energyStorage = */
		this.addComponent(new ComponentEnergyStorage(512, 0, 16000));
		this.addComponent(new GuiProviderAutoSpawner());
	}

	@Override
	protected boolean doWork()
	{
		ItemStack stack = this.inventory.getStackInSlot(0);
		if (stack.isEmpty() || stack.getItem() != ModItems.SafariNetMulti) return false;
		AxisAlignedBB area = this.worldInteractive.getArea();

		for (int i = 0; i < 4; i++)
		{
			Entity entity = ItemSafariNet.getStoredEntityFuzzy(stack, this.getWorld());
			if (entity instanceof EntityLiving)
			{
				if(this.getWorld().getEntitiesWithinAABB(entity.getClass(), area).size() > 10) continue;
				EntityLiving entityLiving = ((EntityLiving) entity);
				entityLiving.onInitialSpawn(this.getWorld().getDifficultyForLocation(this.getPos()), null);
				entityLiving.setCanPickUpLoot(false);

				double x = this.getWorld().rand.nextFloat() * (area.maxX - area.minX) + area.minX;
				double y = this.getPos().getY() + (double) this.world.rand.nextInt(3) - 1;
				double z = this.getWorld().rand.nextFloat() * (area.maxZ - area.minZ) + area.minZ;
				entityLiving.setLocationAndAngles(x, y, z, this.world.rand.nextFloat() * 360.0F, 0.0F);

				if (!this.canSpawnEntity(entityLiving))
				{
					continue;
				}

				this.world.spawnEntity(entityLiving);
			}
		}
		this.setIdleTime((short) 20);
		return true;
	}

	private boolean canSpawnEntity(EntityLiving entity)
	{
		AxisAlignedBB boundingBox = entity.getEntityBoundingBox();
		return this.world.getCollisionBoxes(entity, boundingBox).isEmpty()
				&& (!this.world.containsAnyLiquid(boundingBox) || entity.isCreatureType(EnumCreatureType.WATER_CREATURE, false));
	}
}
