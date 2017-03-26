package net.xalcon.minefactory.common.creativetabs;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.xalcon.minefactory.common.ModBlocks;

public class CreativeTabSirenityMachines extends CreativeTabs
{
	public static final CreativeTabSirenityMachines Instance = new CreativeTabSirenityMachines();

	public CreativeTabSirenityMachines()
	{
		super("machines");
	}

	@Override
	public ItemStack getTabIconItem()
	{
		return new ItemStack(ModBlocks.MachineBreeder, 1);
	}
}
