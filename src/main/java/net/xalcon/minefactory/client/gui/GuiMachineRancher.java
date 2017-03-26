package net.xalcon.minefactory.client.gui;

import net.xalcon.minefactory.client.gui.widgets.WidgetPowerWorkGauge;
import net.xalcon.minefactory.common.GuiType;
import net.xalcon.minefactory.common.container.ContainerMachineRancher;

public class GuiMachineRancher extends GuiBase
{
	public GuiMachineRancher(GuiType.ContextInfo context)
	{
		super(new ContainerMachineRancher(context), context);
		this.widgets.add(new WidgetPowerWorkGauge(7, 16,
				() -> new WidgetPowerWorkGauge.BarData("Power", 0.5f),
				() -> new WidgetPowerWorkGauge.BarData("Work", 0.5f)));
	}
}
