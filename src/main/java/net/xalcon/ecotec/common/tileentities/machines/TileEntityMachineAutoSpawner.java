package net.xalcon.ecotec.common.tileentities.machines;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EnumCreatureType;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraftforge.items.ItemStackHandler;
import net.xalcon.ecotec.common.init.ModBlocks;
import net.xalcon.ecotec.common.init.ModItems;
import net.xalcon.ecotec.common.items.ItemSafariNet;
import net.xalcon.ecotec.common.tileentities.TileEntityMachineWorldInteractive;

public class TileEntityMachineAutoSpawner extends TileEntityMachineWorldInteractive
{
	public TileEntityMachineAutoSpawner()
	{
		super(2, false);
	}

	@Override
	protected ItemStackHandler createInventory()
	{
		return new ItemStackHandler(1);
	}

	@Override
	public String getUnlocalizedName()
	{
		return ModBlocks.MachineAutoSpawner.getUnlocalizedName();
	}

	@Override
	public int getMaxIdleTicks()
	{
		return 50;
	}

	@Override
	public int getMaxProgressTicks()
	{
		return 200;
	}

	@Override
	protected boolean doWork()
	{
		ItemStack stack = this.inventory.getStackInSlot(0);
		if (stack.isEmpty() || stack.getItem() != ModItems.SafariNetMulti) return false;

		int radius = this.getWorkRadius();
		AxisAlignedBB area = new AxisAlignedBB(this.getPos()).expand(radius, 1, radius);

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
		this.setIdleTicks(20);
		return true;
	}

	private boolean canSpawnEntity(EntityLiving entity)
	{
		AxisAlignedBB boundingBox = entity.getEntityBoundingBox();
		return this.world.getCollisionBoxes(entity, boundingBox).isEmpty()
				&& (!this.world.containsAnyLiquid(boundingBox) || entity.isCreatureType(EnumCreatureType.WATER_CREATURE, false));
	}
}
