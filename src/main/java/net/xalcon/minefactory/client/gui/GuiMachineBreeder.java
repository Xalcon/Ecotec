package net.xalcon.minefactory.client.gui;

import net.xalcon.minefactory.client.gui.widgets.WidgetPowerWorkGauge;
import net.xalcon.minefactory.common.GuiType;
import net.xalcon.minefactory.common.container.ContainerMachineBreeder;

public class GuiMachineBreeder extends GuiBase
{
	public GuiMachineBreeder(GuiType.ContextInfo context)
	{
		super(new ContainerMachineBreeder(context), context);
		this.widgets.add(new WidgetPowerWorkGauge(7, 16,
				() -> new WidgetPowerWorkGauge.BarData("Power", 0.5f),
				() -> new WidgetPowerWorkGauge.BarData("Work", 0.5f)));
	}
}
