package net.xalcon.minefactory.client.gui;

import net.xalcon.minefactory.client.gui.widgets.WidgetFluidGauge;
import net.xalcon.minefactory.client.gui.widgets.WidgetPowerWorkGauge;
import net.xalcon.minefactory.common.GuiType;
import net.xalcon.minefactory.common.container.ContainerMachineRancher;
import net.xalcon.minefactory.common.init.ModBlocks;
import net.xalcon.minefactory.common.tileentities.machines.TileEntityMachineRancher;

public class GuiMachineRancher extends GuiBase
{
	public GuiMachineRancher(GuiType.ContextInfo context)
	{
		super(new ContainerMachineRancher(context), context);
		TileEntityMachineRancher rancher = (TileEntityMachineRancher) context.getWorld().getTileEntity(context.getPos());

		this.widgets.add(new WidgetPowerWorkGauge(7, 16,
				() -> new WidgetPowerWorkGauge.BarData("Power", 0.5f),
				() -> new WidgetPowerWorkGauge.BarData("Work", 0.5f)));
		this.widgets.add(new WidgetFluidGauge(151, 15, rancher.getMilkTank()));
		this.widgets.add(new WidgetFluidGauge(133, 15, rancher.getMushroomSoupTank()));
	}
}
