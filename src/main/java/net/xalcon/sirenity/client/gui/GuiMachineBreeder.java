package net.xalcon.sirenity.client.gui;

import net.minecraftforge.fluids.FluidRegistry;
import net.xalcon.sirenity.client.gui.widgets.WidgetFluidGauge;
import net.xalcon.sirenity.client.gui.widgets.WidgetPowerWorkGauge;
import net.xalcon.sirenity.common.GuiType;
import net.xalcon.sirenity.common.container.ContainerMachineBreeder;

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
