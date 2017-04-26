package net.xalcon.ecotec.client.gui;

import net.xalcon.ecotec.client.gui.widgets.WidgetPowerWorkGauge;
import net.xalcon.ecotec.common.GuiType;
import net.xalcon.ecotec.common.inventory.ContainerMachinePlanter;
import net.xalcon.ecotec.common.tileentities.TileEntityMachinePowered;
import net.xalcon.ecotec.common.tileentities.agriculture.TileEntityMachinePlanter;

public class GuiMachinePlanter extends GuiBase<TileEntityMachinePlanter>
{
	public GuiMachinePlanter(GuiType.ContextInfo context)
	{
		super(new ContainerMachinePlanter(context), context);

		this.widgets.add(new WidgetPowerWorkGauge(7, 16, this.tileEntity));
	}
}
