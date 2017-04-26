package net.xalcon.ecotec.common.handler;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.IGuiHandler;
import net.xalcon.ecotec.common.GuiRegistry;

import javax.annotation.Nullable;

public class ModGuiHandler implements IGuiHandler
{
	@Override
	public Object getServerGuiElement(int guiId, @Nullable EntityPlayer player, @Nullable World world, int x, int y, int z)
	{
		if (player == null || world == null) return null;
		return GuiRegistry.fromId(guiId).getServerGuiElement(player, world, x, y, z);
	}

	@Override
	public Object getClientGuiElement(int guiId, @Nullable EntityPlayer player, @Nullable World world, int x, int y, int z)
	{
		if (player == null || world == null) return null;
		return GuiRegistry.fromId(guiId).getClientGuiElement(player, world, x, y, z);
	}
}
