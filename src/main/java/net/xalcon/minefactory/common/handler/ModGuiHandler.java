package net.xalcon.minefactory.common.handler;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.IGuiHandler;
import net.xalcon.minefactory.common.GuiType;

import javax.annotation.Nullable;

public class ModGuiHandler implements IGuiHandler
{
	@Nullable
	@Override
	public Object getServerGuiElement(int guiId, EntityPlayer player, World world, int x, int y, int z)
	{
		return GuiType.fromId(guiId).getServerFactory().apply(new GuiType.ContextInfo(player, world, x, y, z));
	}

	@Nullable
	@Override
	public Object getClientGuiElement(int guiId, EntityPlayer player, World world, int x, int y, int z)
	{
		return GuiType.fromId(guiId).getClientFactory().apply(new GuiType.ContextInfo(player, world, x, y, z));
	}
}
