package net.xalcon.ecotec.client.gui;

import net.xalcon.ecotec.common.inventories.ContainerMachineAutoEnchanter;
import net.xalcon.ecotec.common.inventories.GuiElementContext;
import net.xalcon.ecotec.common.tileentities.machines.TileEntityMachineAutoEnchanter;

public class GuiMachineAutoEnchanter extends GuiBase<TileEntityMachineAutoEnchanter>
{
	public GuiMachineAutoEnchanter(GuiElementContext<TileEntityMachineAutoEnchanter> context)
	{
		super(new ContainerMachineAutoEnchanter(context), context);
	}
}
