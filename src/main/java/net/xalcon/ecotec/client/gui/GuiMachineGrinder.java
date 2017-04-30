package net.xalcon.ecotec.client.gui;

import net.minecraftforge.energy.CapabilityEnergy;
import net.xalcon.ecotec.client.gui.widgets.WidgetFluidGauge;
import net.xalcon.ecotec.client.gui.widgets.WidgetPowerGauge;
import net.xalcon.ecotec.common.inventories.ContainerMachineGrinder;
import net.xalcon.ecotec.common.inventories.GuiElementContext;
import net.xalcon.ecotec.common.tileentities.machines.TileEntityMachineGrinder;

public class GuiMachineGrinder extends GuiBase<TileEntityMachineGrinder>
{
	public GuiMachineGrinder(GuiElementContext<TileEntityMachineGrinder> context)
	{
		super(new ContainerMachineGrinder(context), context);

		this.widgets.add(new WidgetPowerGauge(7 + 16, 16, this.tileEntity.getCapability(CapabilityEnergy.ENERGY, null), 0xFFFF0000));
		this.widgets.add(new WidgetPowerGauge(7 + 32, 16, this.tileEntity.getCapability(CapabilityEnergy.ENERGY, null), 0xFFFFCC00));
		this.widgets.add(new WidgetPowerGauge(7 + 48, 16, this.tileEntity.getCapability(CapabilityEnergy.ENERGY, null), 0xFF00FF00));
		this.widgets.add(new WidgetPowerGauge(7 + 64, 16, this.tileEntity.getCapability(CapabilityEnergy.ENERGY, null), 0xFF0099FF));
		this.widgets.add(new WidgetFluidGauge(151, 15, this.tileEntity.getXpTank(), true));
	}
}
