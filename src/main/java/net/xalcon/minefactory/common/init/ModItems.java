package net.xalcon.minefactory.common.init;

import net.minecraftforge.fml.common.registry.GameRegistry;
import net.xalcon.minefactory.common.creativetabs.CreativeTabMinefactoryMachines;
import net.xalcon.minefactory.common.items.ItemBase;
import net.xalcon.minefactory.common.items.ItemMachineRangeUpgrade;
import net.xalcon.minefactory.common.items.ItemSafariNet;

public class ModItems
{
	public static ItemSafariNet SafariNetSingle;
	public static ItemSafariNet SafariNetMulti;
	public static ItemMachineRangeUpgrade MachineRangeUpgrade;

	public static void init()
	{
		SafariNetSingle = register(new ItemSafariNet(false));
		SafariNetMulti = register(new ItemSafariNet(true));
		MachineRangeUpgrade = register(new ItemMachineRangeUpgrade());
	}

	private static <T extends ItemBase> T register(T item)
	{
		item.setCreativeTab(CreativeTabMinefactoryMachines.Instance);
		GameRegistry.register(item);
		item.registerItemModels();
		return item;
	}
}
