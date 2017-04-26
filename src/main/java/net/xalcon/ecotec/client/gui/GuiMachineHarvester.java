package net.xalcon.ecotec.client.gui;

import net.xalcon.ecotec.client.gui.widgets.WidgetFluidGauge;
import net.xalcon.ecotec.client.gui.widgets.WidgetPowerWorkGauge;
import net.xalcon.ecotec.common.GuiType;
import net.xalcon.ecotec.common.inventory.ContainerMachineHarvester;
import net.xalcon.ecotec.common.tileentities.agriculture.TileEntityMachineHarvester;

public class GuiMachineHarvester extends GuiBase
{
	public GuiMachineHarvester(GuiType.ContextInfo context)
	{
		super(new ContainerMachineHarvester(context), context);

		this.widgets.add(new WidgetPowerWorkGauge(7, 16,context.getTileEntity()));

		this.widgets.add(new WidgetFluidGauge(151, 15, ((TileEntityMachineHarvester) this.tileEntity).getSludgeTank()));
	}
}
