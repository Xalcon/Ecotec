package net.xalcon.minefactory.common.init;

import net.minecraft.item.Item;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.xalcon.minefactory.common.creativetabs.CreativeTabMinefactoryMachines;
import net.xalcon.minefactory.common.items.ItemBase;
import net.xalcon.minefactory.common.items.ItemSafariNet;

public class ModItems
{
	public static ItemSafariNet SafariNet;

	public static void init()
	{
		SafariNet = register(new ItemSafariNet());
	}

	private static <T extends ItemBase> T register(T item)
	{
		item.setCreativeTab(CreativeTabMinefactoryMachines.Instance);
		GameRegistry.register(item);
		item.registerItemModels();
		return item;
	}
}
