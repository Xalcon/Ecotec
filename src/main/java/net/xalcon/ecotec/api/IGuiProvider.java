package net.xalcon.ecotec.api;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.xalcon.ecotec.api.components.IEcotecComponent;

import javax.annotation.Nullable;

public interface IGuiProvider extends IEcotecComponent<IGuiProvider>
{
	/**
	 * Returns the server side gui element
	 * @param guiId the gui id that was requested by openGui
	 * @param player the requesting player
	 * @param world the world the player is in
	 * @param pos the position of the gui provider
	 * @return the gui container
	 */
	Container getServerGuiElement(int guiId, @Nullable EntityPlayer player, @Nullable World world, BlockPos pos);

	/**
	 * Returns the client side gui element
	 * @param guiId the gui id that was requested by openGui
	 * @param player the requesting player
	 * @param world the world the player is in
	 * @param pos the position of the gui provider
	 * @return the gui
	 */
	@SideOnly(Side.CLIENT)
	Object getClientGuiElement(int guiId, @Nullable EntityPlayer player, @Nullable World world, BlockPos pos);
}
