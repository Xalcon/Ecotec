package net.xalcon.minefactory.common.creativetabs;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;

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
		return new ItemStack(Blocks.LIT_FURNACE);
	}
}
