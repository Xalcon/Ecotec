package net.xalcon.ecotec.client.gui;

import net.xalcon.ecotec.client.gui.widgets.WidgetPowerWorkGauge;
import net.xalcon.ecotec.common.GuiType;
import net.xalcon.ecotec.common.inventory.ContainerMachineAutoSpawner;
import net.xalcon.ecotec.common.tileentities.TileEntityMachinePowered;

public class GuiMachineAutoSpawner extends GuiBase
{
	public GuiMachineAutoSpawner(GuiType.ContextInfo context)
	{
		super(new ContainerMachineAutoSpawner(context), context);

		this.widgets.add(new WidgetPowerWorkGauge(7, 16, context.getTileEntity()));
	}
}
