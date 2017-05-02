package net.xalcon.ecotec.api.components;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.xalcon.ecotec.api.components.wrappers.IContainerSlotHandler;
import net.xalcon.ecotec.api.components.wrappers.IGuiWidgetHandler;
import net.xalcon.ecotec.client.gui.GuiBase;
import net.xalcon.ecotec.common.container.ContainerBase;
import net.xalcon.ecotec.common.init.ModCaps;

public interface IGuiProvider extends IEcotecComponent<IGuiProvider>
{
	/**
	 * Allows the gui provider to add widgets to the gui element
	 * @param widgetHandler the widget list handler
	 */
	@SideOnly(Side.CLIENT)
	void addWidgets(IGuiWidgetHandler widgetHandler);

	/**
	 * Allows the gui provider to add slots to a guiprovider
	 * @param slotHandler the guiprovider slot handler
	 */
	void addSlots(IContainerSlotHandler slotHandler);

	/**
	 * Returns the server side gui element
	 * @param guiId the gui id that was requested by openGui
	 * @param player the requesting player
	 * @param world the world the player is in
	 * @param pos the position of the gui provider
	 * @return the gui guiprovider
	 */
	default ContainerBase getServerGuiElement(int guiId, EntityPlayer player, World world, BlockPos pos)
	{
		return new ContainerBase(player, world.getTileEntity(pos));
	}

	/**
	 * Returns the client side gui element
	 * @param guiId the gui id that was requested by openGui
	 * @param player the requesting player
	 * @param world the world the player is in
	 * @param pos the position of the gui provider
	 * @return the gui
	 */
	@SideOnly(Side.CLIENT)
	default Object getClientGuiElement(int guiId, EntityPlayer player, World world, BlockPos pos)
	{
		return new GuiBase(player, world.getTileEntity(pos), ContainerBase::new);
	}

	@Override
	default Capability<IGuiProvider> getCapability() { return ModCaps.getGuiProviderCap(); }
}
