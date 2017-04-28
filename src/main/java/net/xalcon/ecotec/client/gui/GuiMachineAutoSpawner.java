package net.xalcon.ecotec.client.gui;

import net.xalcon.ecotec.client.gui.widgets.WidgetPowerGauge;
import net.xalcon.ecotec.common.inventories.ContainerMachineAutoSpawner;
import net.xalcon.ecotec.common.inventories.GuiElementContext;
import net.xalcon.ecotec.common.tileentities.machines.TileEntityMachineAutoSpawner;

public class GuiMachineAutoSpawner extends GuiBase<TileEntityMachineAutoSpawner>
{
	public GuiMachineAutoSpawner(GuiElementContext<TileEntityMachineAutoSpawner> context)
	{
		super(new ContainerMachineAutoSpawner(context), context);

		this.widgets.add(new WidgetPowerGauge(7, 16, this.tileEntity));
	}
}
