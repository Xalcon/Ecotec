package net.xalcon.ecotec.client.gui;

import net.xalcon.ecotec.client.gui.widgets.WidgetFluidGauge;
import net.xalcon.ecotec.client.gui.widgets.WidgetPowerWorkGauge;
import net.xalcon.ecotec.common.inventory.ContainerMachineRancher;
import net.xalcon.ecotec.common.inventory.GuiElementContext;
import net.xalcon.ecotec.common.tileentities.agriculture.TileEntityMachineRancher;

public class GuiMachineRancher extends GuiBase<TileEntityMachineRancher>
{
	public GuiMachineRancher(GuiElementContext<TileEntityMachineRancher> context)
	{
		super(new ContainerMachineRancher(context), context);

		this.widgets.add(new WidgetPowerWorkGauge(7, 16, this.tileEntity));
		this.widgets.add(new WidgetFluidGauge(151, 15, this.tileEntity.getMilkTank(), false));
		this.widgets.add(new WidgetFluidGauge(133, 15, this.tileEntity.getMushroomSoupTank(), false));
	}
}
