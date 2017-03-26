package net.xalcon.sirenity.client.gui;

import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.inventory.IInventory;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fluids.FluidRegistry;
import net.xalcon.sirenity.SirenityMod;
import net.xalcon.sirenity.client.gui.widgets.GuiWidget;
import net.xalcon.sirenity.client.gui.widgets.WidgetFluidGauge;
import net.xalcon.sirenity.client.gui.widgets.WidgetPowerWorkGauge;
import net.xalcon.sirenity.common.GuiType;
import net.xalcon.sirenity.common.container.ContainerMachineHarvester;

import java.util.ArrayList;

public class GuiMachineHarvester extends GuiBase
{
	public GuiMachineHarvester(GuiType.ContextInfo context)
	{
		super(new ContainerMachineHarvester(context), context);
		this.widgets.add(new WidgetPowerWorkGauge(7, 16,
				() -> new WidgetPowerWorkGauge.BarData("Power", 0.5f),
				() -> new WidgetPowerWorkGauge.BarData("Work", 0.5f)));

		this.widgets.add(new WidgetFluidGauge(176 - 18 - 7, 16,
				() -> new WidgetFluidGauge.FluidData("Fluidity", FluidRegistry.getFluid("water"), 0.4f)));
	}
}
