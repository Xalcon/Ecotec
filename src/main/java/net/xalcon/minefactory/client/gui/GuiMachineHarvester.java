package net.xalcon.minefactory.client.gui;

import net.xalcon.minefactory.client.gui.widgets.WidgetPowerWorkGauge;
import net.xalcon.minefactory.common.GuiType;
import net.xalcon.minefactory.common.inventory.ContainerMachineHarvester;

public class GuiMachineHarvester extends GuiBase
{
	public GuiMachineHarvester(GuiType.ContextInfo context)
	{
		super(new ContainerMachineHarvester(context), context);
		this.widgets.add(new WidgetPowerWorkGauge(7, 16,
				() -> new WidgetPowerWorkGauge.BarData("Power", 0.5f),
				() -> new WidgetPowerWorkGauge.BarData("Work", 0.5f)));

		/*this.widgets.add(new WidgetFluidGauge(176 - 18 - 7, 16,
				() -> new WidgetFluidGauge.FluidData("Fluidity", FluidRegistry.getFluid("water"), 0.4f)));*/
	}
}
