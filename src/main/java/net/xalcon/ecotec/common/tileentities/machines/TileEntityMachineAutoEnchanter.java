package net.xalcon.ecotec.common.tileentities.machines;

import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.item.ItemStack;
import net.minecraftforge.items.ItemStackHandler;
import net.xalcon.ecotec.common.init.ModBlocks;
import net.xalcon.ecotec.common.inventories.itemstackhandler.ItemStackHandlerEnchanter;
import net.xalcon.ecotec.common.tileentities.TileEntityMachinePowered;

public class TileEntityMachineAutoEnchanter extends TileEntityMachinePowered
{
	@Override
	public String getUnlocalizedName()
	{
		return ModBlocks.MachineAutoEnchanter.getUnlocalizedName();
	}

	@Override
	public int getMaxIdleTicks()
	{
		return 200;
	}

	@Override
	public int getMaxProgressTicks()
	{
		return 0;
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

	@Override
	protected ItemStackHandler createInventory()
	{
		return new ItemStackHandlerEnchanter();
	}
}
