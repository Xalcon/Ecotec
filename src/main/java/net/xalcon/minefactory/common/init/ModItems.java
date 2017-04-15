package net.xalcon.minefactory.common.init;

import net.minecraftforge.fml.common.registry.GameRegistry;
import net.xalcon.minefactory.MinefactoryMod;
import net.xalcon.minefactory.common.CommonProxy;
import net.xalcon.minefactory.common.creativetabs.CreativeTabMinefactoryMachines;
import net.xalcon.minefactory.common.items.ItemBase;
import net.xalcon.minefactory.common.items.ItemDebugger;
import net.xalcon.minefactory.common.items.ItemMachineRangeUpgrade;
import net.xalcon.minefactory.common.items.ItemSafariNet;

public class ModItems
{
	public static ItemSafariNet SafariNetSingle;
	public static ItemSafariNet SafariNetMulti;
	public static ItemMachineRangeUpgrade MachineRangeUpgrade;

	public static void init()
	{
		CommonProxy proxy = MinefactoryMod.Proxy;
		SafariNetSingle = proxy.register(new ItemSafariNet(false));
		SafariNetMulti = proxy.register(new ItemSafariNet(true));
		MachineRangeUpgrade = proxy.register(new ItemMachineRangeUpgrade());
		proxy.register(new ItemDebugger());
	}
}
