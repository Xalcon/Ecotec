package net.xalcon.ecotec.common.tileentities.machines;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EnumCreatureType;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidUtil;
import net.xalcon.ecotec.common.components.*;
import net.xalcon.ecotec.common.container.guiprovider.GuiProviderAutoSpawner;
import net.xalcon.ecotec.common.init.ModBlocks;
import net.xalcon.ecotec.common.init.ModFluids;
import net.xalcon.ecotec.common.init.ModItems;
import net.xalcon.ecotec.common.items.ItemSafariNet;
import net.xalcon.ecotec.common.tileentities.TileEntityTickable;

public class TileEntityMachineAutoSpawner extends TileEntityTickable
{
	private final ComponentItemHandler inventory;
	private final ComponentWorldInteractiveFrontal worldInteractive;
	private final ComponentEnergyStorage energyStorage;
	private final ComponentFluidTank fluidTank;

	public TileEntityMachineAutoSpawner()
	{
		this.inventory = this.addComponent(new ComponentItemHandler(1));
		this.fluidTank = this.addComponent(new ComponentFluidTank(ModFluids.FluidMobEssence, 0, 4000));
		this.worldInteractive = this.addComponent(new ComponentWorldInteractiveSelf(1, 1, 0));
		this.energyStorage = this.addComponent(new ComponentEnergyStorage(512, 0, 16000));
		this.addComponent(new GuiProviderAutoSpawner());
		this.addComponent(new ComponentFluidItemInteraction(true, false));
	}

	@Override
	protected boolean doWork()
	{
		ItemStack stack = this.inventory.getStackInSlot(0);
		if (stack.isEmpty() || stack.getItem() != ModItems.SafariNetMulti) return false;
		AxisAlignedBB area = this.worldInteractive.getArea();

		int tries = Math.min(4, this.energyStorage.getEnergyStored() / 8);

		for (int i = 0; i < tries; i++)
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

				this.energyStorage.useEnergy(2000);
				this.world.spawnEntity(entityLiving);
			}
		}
		return false;
	}

	private boolean canSpawnEntity(EntityLiving entity)
	{
		AxisAlignedBB boundingBox = entity.getEntityBoundingBox();
		return this.world.getCollisionBoxes(entity, boundingBox).isEmpty()
				&& (!this.world.containsAnyLiquid(boundingBox) || entity.isCreatureType(EnumCreatureType.WATER_CREATURE, false));
	}

	public ComponentFluidTank getFluidTank()
	{
		return this.fluidTank;
	}
}
