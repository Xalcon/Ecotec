package net.xalcon.ecotec.common.tileentities.agriculture;

import com.mojang.authlib.GameProfile;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.world.WorldServer;
import net.minecraftforge.common.util.FakePlayer;
import net.minecraftforge.common.util.FakePlayerFactory;
import net.xalcon.ecotec.common.components.ComponentEnergyStorage;
import net.xalcon.ecotec.common.components.ComponentItemHandler;
import net.xalcon.ecotec.common.components.ComponentWorldInteractiveFrontal;
import net.xalcon.ecotec.common.tileentities.TileEntityTickable;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

public class TileEntityMachineBreeder extends TileEntityTickable
{
	private final ComponentItemHandler inventory;
	private final ComponentWorldInteractiveFrontal worldInteractive;
	//private final ComponentEnergyStorage energyStorage;

	public TileEntityMachineBreeder()
	{
		this.inventory = this.addComponent(new ComponentItemHandler(9));
		this.worldInteractive = this.addComponent(new ComponentWorldInteractiveFrontal(1));
		/*this.energyStorage = */this.addComponent(new ComponentEnergyStorage(512, 0, 16000));
	}

	@Override
	protected boolean doWork()
	{
		FakePlayer player = FakePlayerFactory.get((WorldServer) this.getWorld(), new GameProfile(new UUID(0x12345678, 0x11223344), "ecotec:breeder"));
		AxisAlignedBB area = this.worldInteractive.getArea();
		List<EntityAnimal> animals = this.getWorld().getEntitiesWithinAABB(EntityAnimal.class, area);
		if(animals.size() > 10) return false;

		for(EntityAnimal entity : animals)
		{
			if (!entity.isInLove() && entity.getGrowingAge() == 0)
			{
				for (int i = 0; i < this.inventory.getSlots(); i++)
				{
					ItemStack stack = this.inventory.getStackInSlot(i);
					if (entity.isBreedingItem(stack))
					{
						entity.setInLove(player);
						stack.shrink(1);
						this.markDirty();
						return true;
					}
				}
			}
		}

		return false;
	}
}
