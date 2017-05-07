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
import net.xalcon.ecotec.common.tileentities.machines.TileEntityMachineSludgeBoiler;

public class GuiProviderSludgeBoiler implements IGuiProvider
{
	private TileEntityMachineSludgeBoiler tileEntity;

	@Override
	public void initialize(ICapabilityProvider provider)
	{
		if(provider instanceof TileEntityMachineSludgeBoiler)
			this.tileEntity = (TileEntityMachineSludgeBoiler) provider;
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

		widgetHandler.addWidget(new WidgetFluidGauge(151, 15, this.tileEntity.getFluidTank(), true));
	}

	@Override
	public void addSlots(EntityPlayer player, IContainerSlotHandler slotHandler)
	{
	}
}
