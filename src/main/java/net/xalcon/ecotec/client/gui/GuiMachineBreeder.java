package net.xalcon.ecotec.client.gui;

import net.xalcon.ecotec.client.gui.widgets.WidgetPowerWorkGauge;
import net.xalcon.ecotec.common.GuiType;
import net.xalcon.ecotec.common.inventory.ContainerMachineBreeder;
import net.xalcon.ecotec.common.tileentities.agriculture.TileEntityMachineBreeder;

public class GuiMachineBreeder extends GuiBase<TileEntityMachineBreeder>
{
	public GuiMachineBreeder(GuiType.ContextInfo context)
	{
		super(new ContainerMachineBreeder(context), context);
		this.widgets.add(new WidgetPowerWorkGauge(7, 16, this.tileEntity));
	}
}
