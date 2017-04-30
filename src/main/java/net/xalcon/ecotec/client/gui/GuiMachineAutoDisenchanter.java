package net.xalcon.ecotec.client.gui;

import net.xalcon.ecotec.common.inventories.ContainerBase;
import net.xalcon.ecotec.common.inventories.ContainerMachineAutoDisenchanter;
import net.xalcon.ecotec.common.inventories.GuiElementContext;
import net.xalcon.ecotec.common.tileentities.machines.TileEntityMachineAutoDisenchanter;

public class GuiMachineAutoDisenchanter extends GuiBase<TileEntityMachineAutoDisenchanter>
{
	public GuiMachineAutoDisenchanter(GuiElementContext<TileEntityMachineAutoDisenchanter> context)
	{
		super(new ContainerMachineAutoDisenchanter(context), context);
	}
}
