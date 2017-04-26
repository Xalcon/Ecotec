package net.xalcon.ecotec.client.gui;

import net.xalcon.ecotec.client.gui.widgets.WidgetPowerWorkGauge;
import net.xalcon.ecotec.common.inventory.ContainerMachinePlanter;
import net.xalcon.ecotec.common.inventory.GuiElementContext;
import net.xalcon.ecotec.common.tileentities.agriculture.TileEntityMachinePlanter;

public class GuiMachinePlanter extends GuiBase<TileEntityMachinePlanter>
{
	public GuiMachinePlanter(GuiElementContext<TileEntityMachinePlanter> context)
	{
		super(new ContainerMachinePlanter(context), context);

		this.widgets.add(new WidgetPowerWorkGauge(7, 16, this.tileEntity));
	}
}
