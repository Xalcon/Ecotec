package net.xalcon.ecotec.common.container.guiprovider;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.energy.CapabilityEnergy;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.xalcon.ecotec.api.components.IGuiProvider;
import net.xalcon.ecotec.api.components.wrappers.IContainerSlotHandler;
import net.xalcon.ecotec.api.components.wrappers.IGuiWidgetHandler;
import net.xalcon.ecotec.client.gui.widgets.WidgetFluidGauge;
import net.xalcon.ecotec.client.gui.widgets.WidgetPowerGauge;
import net.xalcon.ecotec.common.tileentities.machines.TileEntityMachineGrinder;

public class GuiProviderGrinder implements IGuiProvider
{
	private TileEntityMachineGrinder tileEntity;

	@Override
	public void initialize(ICapabilityProvider provider)
	{
		if(provider instanceof TileEntityMachineGrinder)
			this.tileEntity = (TileEntityMachineGrinder) provider;
	}

	@Override
	public void invalidate()
	{
		this.tileEntity = null;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void addWidgets(EntityPlayer player, IGuiWidgetHandler widgetHandler)
	{
		if(this.tileEntity == null) return;

		widgetHandler.addWidget(new WidgetFluidGauge(151, 15, this.tileEntity.getXpTank(), true));
		widgetHandler.addWidget(new WidgetPowerGauge(7 + 16, 16, this.tileEntity.getCapability(CapabilityEnergy.ENERGY, null), 0xFFFF0000));
		widgetHandler.addWidget(new WidgetPowerGauge(7 + 32, 16, this.tileEntity.getCapability(CapabilityEnergy.ENERGY, null), 0xFFFFCC00));
		widgetHandler.addWidget(new WidgetPowerGauge(7 + 48, 16, this.tileEntity.getCapability(CapabilityEnergy.ENERGY, null), 0xFF00FF00));
		widgetHandler.addWidget(new WidgetPowerGauge(7 + 64, 16, this.tileEntity.getCapability(CapabilityEnergy.ENERGY, null), 0xFF0099FF));
	}

	@Override
	public void addSlots(EntityPlayer player, IContainerSlotHandler slotHandler)
	{
	}
}
