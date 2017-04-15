package net.xalcon.minefactory.client.gui;

import net.xalcon.minefactory.client.gui.widgets.WidgetFluidGauge;
import net.xalcon.minefactory.client.gui.widgets.WidgetPowerWorkGauge;
import net.xalcon.minefactory.common.GuiType;
import net.xalcon.minefactory.common.inventory.ContainerMachineGrinder;
import net.xalcon.minefactory.common.tileentities.TileEntityMachinePowered;
import net.xalcon.minefactory.common.tileentities.machines.TileEntityMachineGrinder;

public class GuiMachineGrinder extends GuiBase
{
	public GuiMachineGrinder(GuiType.ContextInfo context)
	{
		super(new ContainerMachineGrinder(context), context);

		TileEntityMachineGrinder grinder = (TileEntityMachineGrinder) context.getWorld().getTileEntity(context.getPos());
		if (grinder == null) return;

		this.widgets.add(new WidgetPowerWorkGauge(7, 16,
				() -> new WidgetPowerWorkGauge.BarData("Power", 0.5f),
				() ->
				{
					int m = ((TileEntityMachinePowered) this.tileEntity).getMaxIdleTicks();
					int c = m - ((TileEntityMachinePowered) this.tileEntity).getIdleTicks();
					float progress = c / (float) m;
					return new WidgetPowerWorkGauge.BarData("Idle (" + c + " / " + m + ")", progress);
				}));
		this.widgets.add(new WidgetFluidGauge(151, 15, grinder.getXpTank()));
	}
}
