package net.xalcon.ecotec.common.container.guiprovider;

import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.xalcon.ecotec.api.components.IGuiProvider;
import net.xalcon.ecotec.api.components.wrappers.IContainerSlotHandler;
import net.xalcon.ecotec.api.components.wrappers.IGuiWidgetHandler;
import net.xalcon.ecotec.client.gui.widgets.WidgetFluidGauge;
import net.xalcon.ecotec.common.tileentities.agriculture.TileEntityMachineHarvester;

public class GuiProviderHarvester implements IGuiProvider
{
	private TileEntityMachineHarvester tileEntity;

	@Override
	public void initialize(ICapabilityProvider provider)
	{
		if(provider instanceof TileEntityMachineHarvester)
			this.tileEntity = (TileEntityMachineHarvester) provider;
	}

	@Override
	public void invalidate()
	{
		this.tileEntity = null;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void addWidgets(IGuiWidgetHandler widgetHandler)
	{
		if(this.tileEntity == null) return;
		widgetHandler.addWidget(new WidgetFluidGauge(151, 15, this.tileEntity.getSludgeTank(), true));
	}

	@Override
	public void addSlots(IContainerSlotHandler slotHandler)
	{
	}
}
