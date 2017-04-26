package net.xalcon.ecotec.client.gui;

import net.xalcon.ecotec.client.gui.widgets.WidgetFluidGauge;
import net.xalcon.ecotec.client.gui.widgets.WidgetPowerWorkGauge;
import net.xalcon.ecotec.common.inventory.ContainerMachineGrinder;
import net.xalcon.ecotec.common.inventory.GuiElementContext;
import net.xalcon.ecotec.common.tileentities.machines.TileEntityMachineGrinder;

public class GuiMachineGrinder extends GuiBase<TileEntityMachineGrinder>
{
	public GuiMachineGrinder(GuiElementContext<TileEntityMachineGrinder> context)
	{
		super(new ContainerMachineGrinder(context), context);

		this.widgets.add(new WidgetPowerWorkGauge(7, 16, this.tileEntity));
		this.widgets.add(new WidgetFluidGauge(151, 15, this.tileEntity.getXpTank(), true));
	}
}
