package net.xalcon.minefactory.client.gui;

import net.xalcon.minefactory.client.gui.widgets.WidgetFluidGauge;
import net.xalcon.minefactory.client.gui.widgets.WidgetPowerWorkGauge;
import net.xalcon.minefactory.common.GuiType;
import net.xalcon.minefactory.common.inventory.ContainerMachineHarvester;
import net.xalcon.minefactory.common.tileentities.agriculture.TileEntityMachineHarvester;

public class GuiMachineHarvester extends GuiBase
{
	public GuiMachineHarvester(GuiType.ContextInfo context)
	{
		super(new ContainerMachineHarvester(context), context);

		this.widgets.add(new WidgetPowerWorkGauge(7, 16,
				() -> new WidgetPowerWorkGauge.BarData("Power", 0.5f),
				() -> new WidgetPowerWorkGauge.BarData("Work", 0.5f)));

		this.widgets.add(new WidgetFluidGauge(151, 15, ((TileEntityMachineHarvester) this.tileEntity).getSludgeTank()));
	}
}
