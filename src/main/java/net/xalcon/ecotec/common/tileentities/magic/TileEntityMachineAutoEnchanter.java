package net.xalcon.ecotec.common.tileentities.magic;

import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.item.ItemStack;
import net.xalcon.ecotec.common.components.ComponentEnergyStorage;
import net.xalcon.ecotec.common.components.ComponentItemHandlerEnchanter;
import net.xalcon.ecotec.common.container.guiprovider.GuiProviderAutoEnchanter;
import net.xalcon.ecotec.common.tileentities.TileEntityTickable;

public class TileEntityMachineAutoEnchanter extends TileEntityTickable
{
	private final ComponentEnergyStorage energyStorage;
	private final ComponentItemHandlerEnchanter inventory;

	public TileEntityMachineAutoEnchanter()
	{
		this.inventory = this.addComponent(new ComponentItemHandlerEnchanter());
		this.energyStorage = this.addComponent(new ComponentEnergyStorage(512, 0, 16000));
		this.addComponent(new GuiProviderAutoEnchanter());
	}

	@Override
	protected boolean doWork()
	{
		if(this.energyStorage.getEnergyStored() < 8000) return false;
		ItemStack itemStack = this.inventory.getStackInSlot(0);
		if(itemStack.isEmpty() || !this.inventory.getStackInSlot(1).isEmpty()) return false;

		EnchantmentHelper.addRandomEnchantment(this.getWorld().rand, itemStack, 30, true);
		this.inventory.setStackInSlot(0, ItemStack.EMPTY);
		this.inventory.setStackInSlot(1, itemStack);
		this.energyStorage.useEnergy(8000);
		return false;
	}
}
