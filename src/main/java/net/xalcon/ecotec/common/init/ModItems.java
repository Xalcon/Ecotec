package net.xalcon.ecotec.common.init;

import net.xalcon.ecotec.EcotecMod;
import net.xalcon.ecotec.common.CommonProxy;
import net.xalcon.ecotec.common.items.ItemMachineRangeUpgrade;
import net.xalcon.ecotec.common.items.ItemSafariNet;

public class ModItems
{
	public static ItemSafariNet SafariNetSingle;
	public static ItemSafariNet SafariNetMulti;
	public static ItemMachineRangeUpgrade MachineRangeUpgrade;

	public static void init()
	{
		CommonProxy proxy = EcotecMod.Proxy;
		SafariNetSingle = proxy.register(new ItemSafariNet(false));
		SafariNetMulti = proxy.register(new ItemSafariNet(true));
		MachineRangeUpgrade = proxy.register(new ItemMachineRangeUpgrade());
	}
}
