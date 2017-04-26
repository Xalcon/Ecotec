package net.xalcon.ecotec.client.gui;

import net.xalcon.ecotec.client.gui.widgets.WidgetPowerWorkGauge;
import net.xalcon.ecotec.common.GuiType;
import net.xalcon.ecotec.common.inventory.ContainerMachineBreeder;

public class GuiMachineBreeder extends GuiBase
{
	public GuiMachineBreeder(GuiType.ContextInfo context)
	{
		super(new ContainerMachineBreeder(context), context);
		this.widgets.add(new WidgetPowerWorkGauge(7, 16, context.getTileEntity()));
	}
}
