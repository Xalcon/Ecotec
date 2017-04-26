package net.xalcon.ecotec.client.gui;

import net.xalcon.ecotec.client.gui.widgets.WidgetFluidGauge;
import net.xalcon.ecotec.client.gui.widgets.WidgetPowerWorkGauge;
import net.xalcon.ecotec.common.GuiType;
import net.xalcon.ecotec.common.inventory.ContainerMachineRancher;
import net.xalcon.ecotec.common.tileentities.agriculture.TileEntityMachineRancher;

public class GuiMachineRancher extends GuiBase
{
	public GuiMachineRancher(GuiType.ContextInfo context)
	{
		super(new ContainerMachineRancher(context), context);
		TileEntityMachineRancher rancher = (TileEntityMachineRancher) context.getWorld().getTileEntity(context.getPos());

		if (rancher == null) return;

		this.widgets.add(new WidgetPowerWorkGauge(7, 16, context.getTileEntity()));
		this.widgets.add(new WidgetFluidGauge(151, 15, rancher.getMilkTank()));
		this.widgets.add(new WidgetFluidGauge(133, 15, rancher.getMushroomSoupTank()));
	}
}
