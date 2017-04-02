package net.xalcon.minefactory.client.gui;

import net.xalcon.minefactory.client.gui.widgets.WidgetFluidGauge;
import net.xalcon.minefactory.client.gui.widgets.WidgetPowerWorkGauge;
import net.xalcon.minefactory.common.GuiType;
import net.xalcon.minefactory.common.container.ContainerMachineGrinder;
import net.xalcon.minefactory.common.tileentities.machines.TileEntityMachineGrinder;

public class GuiMachineGrinder extends GuiBase
{
	public GuiMachineGrinder(GuiType.ContextInfo context)
	{
		super(new ContainerMachineGrinder(context), context);

		TileEntityMachineGrinder grinder = (TileEntityMachineGrinder) context.getWorld().getTileEntity(context.getPos());
		this.widgets.add(new WidgetPowerWorkGauge(7, 16,
				() -> new WidgetPowerWorkGauge.BarData("Power", 0.5f),
				() -> new WidgetPowerWorkGauge.BarData("Work", 0.5f)));
		this.widgets.add(new WidgetFluidGauge(151, 15, grinder.getXpTank()));
	}
}
