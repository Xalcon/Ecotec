package net.xalcon.ecotec.common.tileentities.machines;

import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.item.ItemStack;
import net.minecraftforge.energy.CapabilityEnergy;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.ItemStackHandler;
import net.xalcon.ecotec.common.components.ComponentEnergyStorage;
import net.xalcon.ecotec.common.components.ComponentItemHandlerDisenchanter;
import net.xalcon.ecotec.common.init.ModBlocks;
import net.xalcon.ecotec.common.components.ComponentItemHandlerEnchanter;
import net.xalcon.ecotec.common.tileentities.TileEntityMachinePowered;
import net.xalcon.ecotec.common.tileentities.TileEntityTickable;

public class TileEntityMachineAutoEnchanter extends TileEntityTickable
{
	private final ComponentEnergyStorage energyStorage;
	private final ComponentItemHandlerEnchanter inventory;

	public TileEntityMachineAutoEnchanter()
	{
		this.inventory = this.addCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, new ComponentItemHandlerEnchanter());
		this.energyStorage = this.addCapability(CapabilityEnergy.ENERGY, new ComponentEnergyStorage(512, 0, 16000, this::markForUpdate));
	}

	@Override
	protected boolean doWork()
	{
		ItemStack itemStack = this.inventory.getStackInSlot(0);
		if(itemStack.isEmpty() || !this.inventory.getStackInSlot(1).isEmpty()) return false;

		EnchantmentHelper.addRandomEnchantment(this.getWorld().rand, itemStack, 30, true);
		this.inventory.setStackInSlot(0, ItemStack.EMPTY);
		this.inventory.setStackInSlot(1, itemStack);
		return false;
	}
}
