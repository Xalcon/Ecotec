package net.xalcon.ecotec.client.gui;

import net.xalcon.ecotec.client.gui.widgets.WidgetFluidGauge;
import net.xalcon.ecotec.client.gui.widgets.WidgetPowerGauge;
import net.xalcon.ecotec.common.inventories.ContainerMachineRancher;
import net.xalcon.ecotec.common.inventories.GuiElementContext;
import net.xalcon.ecotec.common.tileentities.agriculture.TileEntityMachineRancher;

public class GuiMachineRancher extends GuiBase<TileEntityMachineRancher>
{
	public GuiMachineRancher(GuiElementContext<TileEntityMachineRancher> context)
	{
		super(new ContainerMachineRancher(context), context);

		this.widgets.add(new WidgetPowerGauge(7, 16, this.tileEntity));
		this.widgets.add(new WidgetFluidGauge(151, 15, this.tileEntity.getMilkTank(), false));
		this.widgets.add(new WidgetFluidGauge(133, 15, this.tileEntity.getMushroomSoupTank(), false));
	}
}
