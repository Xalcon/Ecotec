package net.xalcon.ecotec.common.tileentities.agriculture;

import com.mojang.authlib.GameProfile;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.world.WorldServer;
import net.minecraftforge.common.util.FakePlayer;
import net.minecraftforge.common.util.FakePlayerFactory;
import net.minecraftforge.energy.CapabilityEnergy;
import net.minecraftforge.items.CapabilityItemHandler;
import net.xalcon.ecotec.common.components.ComponentEnergyStorage;
import net.xalcon.ecotec.common.components.ComponentItemHandler;
import net.xalcon.ecotec.common.components.ComponentWorldInteractiveFrontal;
import net.xalcon.ecotec.common.init.ModCaps;
import net.xalcon.ecotec.common.tileentities.TileEntityTickable;

import java.util.UUID;

public class TileEntityMachineBreeder extends TileEntityTickable
{
	private final ComponentItemHandler inventory;
	private final ComponentWorldInteractiveFrontal worldInteractive;
	//private final ComponentEnergyStorage energyStorage;

	public TileEntityMachineBreeder()
	{
		this.inventory = this.addComponent(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, new ComponentItemHandler(9));
		this.worldInteractive = this.addComponent(ModCaps.getWorldInteractiveCap(), new ComponentWorldInteractiveFrontal(1));
		/*this.energyStorage = */this.addComponent(CapabilityEnergy.ENERGY, new ComponentEnergyStorage(512, 0, 16000));
	}

	@Override
	protected boolean doWork()
	{
		FakePlayer player = FakePlayerFactory.get((WorldServer) this.getWorld(), new GameProfile(new UUID(0x12345678, 0x11223344), "ecotec:breeder"));
		AxisAlignedBB area = this.worldInteractive.getArea();
		for (EntityAnimal entity : this.getWorld().getEntitiesWithinAABB(EntityAnimal.class, area))
		{
			if (!entity.isInLove() && entity.getGrowingAge() == 0)
			{
				for (int i = 0; i < this.inventory.getSlots(); i++)
				{
					ItemStack stack = this.inventory.getStackInSlot(i);
					if (entity.isBreedingItem(stack))
					{
						entity.setInLove(player); // TODO: Add breeding cap
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
