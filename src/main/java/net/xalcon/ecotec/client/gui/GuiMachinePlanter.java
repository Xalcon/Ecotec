package net.xalcon.ecotec.client.gui;

import net.xalcon.ecotec.client.gui.widgets.WidgetPowerGauge;
import net.xalcon.ecotec.common.inventories.ContainerMachinePlanter;
import net.xalcon.ecotec.common.inventories.GuiElementContext;
import net.xalcon.ecotec.common.tileentities.agriculture.TileEntityMachinePlanter;

public class GuiMachinePlanter extends GuiBase<TileEntityMachinePlanter>
{
	public GuiMachinePlanter(GuiElementContext<TileEntityMachinePlanter> context)
	{
		super(new ContainerMachinePlanter(context), context);
	}
}
