package net.xalcon.ecotec.client.gui;

import net.xalcon.ecotec.client.gui.widgets.WidgetPowerWorkGauge;
import net.xalcon.ecotec.common.GuiType;
import net.xalcon.ecotec.common.inventory.ContainerMachinePlanter;
import net.xalcon.ecotec.common.tileentities.TileEntityMachinePowered;

public class GuiMachinePlanter extends GuiBase
{
	public GuiMachinePlanter(GuiType.ContextInfo context)
	{
		super(new ContainerMachinePlanter(context), context);

		this.widgets.add(new WidgetPowerWorkGauge(7, 16, context.getTileEntity()));
	}
}