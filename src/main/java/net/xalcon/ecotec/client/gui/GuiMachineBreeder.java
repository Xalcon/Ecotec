package net.xalcon.ecotec.client.gui;

import net.xalcon.ecotec.common.inventories.ContainerMachineBreeder;
import net.xalcon.ecotec.common.inventories.GuiElementContext;
import net.xalcon.ecotec.common.tileentities.agriculture.TileEntityMachineBreeder;

public class GuiMachineBreeder extends GuiBase<TileEntityMachineBreeder>
{
	public GuiMachineBreeder(GuiElementContext<TileEntityMachineBreeder> context)
	{
		super(new ContainerMachineBreeder(context), context);
	}
}
