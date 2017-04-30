package net.xalcon.ecotec.client.gui;

import net.xalcon.ecotec.client.gui.widgets.WidgetFluidGauge;
import net.xalcon.ecotec.client.gui.widgets.WidgetPowerGauge;
import net.xalcon.ecotec.common.inventories.ContainerMachineHarvester;
import net.xalcon.ecotec.common.inventories.GuiElementContext;
import net.xalcon.ecotec.common.tileentities.agriculture.TileEntityMachineHarvester;

public class GuiMachineHarvester extends GuiBase<TileEntityMachineHarvester>
{
	public GuiMachineHarvester(GuiElementContext<TileEntityMachineHarvester> context)
	{
		super(new ContainerMachineHarvester(context), context);

		this.widgets.add(new WidgetFluidGauge(151, 15, this.tileEntity.getSludgeTank(), true));
	}
}
