package net.xalcon.ecotec.api.components;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
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
	 * @param player the player accessing this gui
	 */
	@SideOnly(Side.CLIENT)
	void addWidgets(EntityPlayer player, IGuiWidgetHandler widgetHandler);

	/**
	 * Allows the gui provider to add slots to a guiprovider
	 * @param slotHandler the guiprovider slot handler
	 * @param player the player accessing this gui
	 */
	void addSlots(EntityPlayer player, IContainerSlotHandler slotHandler);

	/**
	 * Returns the height of the container content. Default is 76
	 * @return container height in pixel
	 */
	default int getContentHeight() { return 78; }

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
		return new GuiBase(new ContainerBase(player, world.getTileEntity(pos)), player, world.getTileEntity(pos));
	}

	@Override
	default Capability<IGuiProvider> getCapability() { return ModCaps.getGuiProviderCap(); }
}
