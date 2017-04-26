package net.xalcon.ecotec.client.gui;

import net.xalcon.ecotec.client.gui.widgets.WidgetFluidGauge;
import net.xalcon.ecotec.client.gui.widgets.WidgetPowerWorkGauge;
import net.xalcon.ecotec.common.GuiType;
import net.xalcon.ecotec.common.inventory.ContainerMachineGrinder;
import net.xalcon.ecotec.common.tileentities.TileEntityMachinePowered;
import net.xalcon.ecotec.common.tileentities.machines.TileEntityMachineGrinder;

public class GuiMachineGrinder extends GuiBase
{
	public GuiMachineGrinder(GuiType.ContextInfo context)
	{
		super(new ContainerMachineGrinder(context), context);

		TileEntityMachineGrinder grinder = (TileEntityMachineGrinder) context.getWorld().getTileEntity(context.getPos());
		if (grinder == null) return;

		this.widgets.add(new WidgetPowerWorkGauge(7, 16,context.getTileEntity()));
		this.widgets.add(new WidgetFluidGauge(151, 15, grinder.getXpTank()));
	}
}
