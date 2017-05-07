package net.xalcon.ecotec.common.container.guiprovider;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.fluids.FluidTank;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;
import net.minecraftforge.fluids.capability.IFluidHandler;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.SlotItemHandler;
import net.xalcon.ecotec.api.components.IGuiProvider;
import net.xalcon.ecotec.api.components.wrappers.IContainerSlotHandler;
import net.xalcon.ecotec.api.components.wrappers.IGuiWidgetHandler;
import net.xalcon.ecotec.client.gui.widgets.WidgetFluidGauge;
import net.xalcon.ecotec.common.tileentities.machines.TileEntityMachineAutoSpawner;

public class GuiProviderAutoSpawner implements IGuiProvider
{
	private IItemHandler inventory;
	private FluidTank fluidTank;

	@Override
	public void initialize(ICapabilityProvider provider)
	{
		this.inventory = provider.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, null);
		if(provider instanceof TileEntityMachineAutoSpawner)
			this.fluidTank = ((TileEntityMachineAutoSpawner) provider).getFluidTank();
	}

	@Override
	public void invalidate()
	{
		this.inventory = null;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void addWidgets(EntityPlayer player, IGuiWidgetHandler widgetHandler)
	{
		if(this.fluidTank != null)
			widgetHandler.addWidget(new WidgetFluidGauge(151, 15, this.fluidTank, true));
	}

	@Override
	public void addSlots(EntityPlayer player, IContainerSlotHandler slotHandler)
	{
		if(this.inventory == null) return;
		slotHandler.addSlot(new SlotItemHandler(this.inventory, 0, 79, 34));
	}
}
