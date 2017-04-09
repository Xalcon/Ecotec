package net.xalcon.minefactory.client.gui;

import net.minecraft.inventory.Container;
import net.xalcon.minefactory.client.gui.widgets.WidgetPowerWorkGauge;
import net.xalcon.minefactory.common.GuiType;
import net.xalcon.minefactory.common.inventory.ContainerMachineAutoSpawner;
import net.xalcon.minefactory.common.tileentities.TileEntityMachinePowered;

public class GuiMachineAutoSpawner extends GuiBase
{
	public GuiMachineAutoSpawner(GuiType.ContextInfo context)
	{
		super(new ContainerMachineAutoSpawner(context), context);

		this.widgets.add(new WidgetPowerWorkGauge(7, 16,
				() -> new WidgetPowerWorkGauge.BarData("Power", 0.5f),
				() ->
				{
					int m = ((TileEntityMachinePowered)this.tileEntity).getMaxIdleTicks();
					int c = m - ((TileEntityMachinePowered)this.tileEntity).getIdleTicks();
					float progress = c / (float)m;
					return new WidgetPowerWorkGauge.BarData("Idle (" + c + " / " + m + ")", progress);
				}));
	}
}
