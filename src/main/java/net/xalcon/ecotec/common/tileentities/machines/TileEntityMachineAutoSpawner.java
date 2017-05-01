package net.xalcon.ecotec.common.tileentities.machines;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EnumCreatureType;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraftforge.energy.CapabilityEnergy;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.ItemStackHandler;
import net.xalcon.ecotec.common.components.ComponentEnergyStorage;
import net.xalcon.ecotec.common.components.ComponentItemHandler;
import net.xalcon.ecotec.common.components.ComponentItemHandlerEnchanter;
import net.xalcon.ecotec.common.components.ComponentWorldInteractiveFrontal;
import net.xalcon.ecotec.common.init.ModBlocks;
import net.xalcon.ecotec.common.init.ModCaps;
import net.xalcon.ecotec.common.init.ModItems;
import net.xalcon.ecotec.common.items.ItemSafariNet;
import net.xalcon.ecotec.common.tileentities.TileEntityMachineWorldInteractive;
import net.xalcon.ecotec.common.tileentities.TileEntityTickable;

public class TileEntityMachineAutoSpawner extends TileEntityTickable
{
	private final ComponentItemHandler inventory;
	private final ComponentWorldInteractiveFrontal worldInteractive;
	private final ComponentEnergyStorage energyStorage;

	public TileEntityMachineAutoSpawner()
	{
		this.inventory = this.addCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, new ComponentItemHandler(1));
		this.worldInteractive = this.addCapability(ModCaps.WORLD_INTERACTIVE_CAP, new ComponentWorldInteractiveFrontal(1));
		this.energyStorage = this.addCapability(CapabilityEnergy.ENERGY, new ComponentEnergyStorage(512, 0, 16000, this::markForUpdate));
	}

	@Override
	protected boolean doWork()
	{
		ItemStack stack = this.inventory.getStackInSlot(0);
		if (stack.isEmpty() || stack.getItem() != ModItems.SafariNetMulti) return false;
		AxisAlignedBB area = this.worldInteractive.getArea(this.getPos(), null);

		// TODO: Add spawn cap
		for (int i = 0; i < 4; i++)
		{
			Entity entity = ItemSafariNet.getStoredEntityFuzzy(stack, this.getWorld());
			if (entity instanceof EntityLiving)
			{
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
