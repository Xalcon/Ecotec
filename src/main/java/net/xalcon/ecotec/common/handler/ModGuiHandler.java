package net.xalcon.ecotec.common.handler;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.IGuiHandler;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.xalcon.ecotec.api.components.IGuiProvider;
import net.xalcon.ecotec.common.init.ModCaps;

import javax.annotation.Nullable;

public class ModGuiHandler implements IGuiHandler
{
	@Override
	public Object getServerGuiElement(int guiId, @Nullable EntityPlayer player, @Nullable World world, int x, int y, int z)
	{
		if (player == null || world == null) return null;
		BlockPos pos = new BlockPos(x, y, z);
		TileEntity tile = world.getTileEntity(pos);
		if(tile != null)
		{
			IGuiProvider guiProvider = tile.getCapability(ModCaps.getGuiProviderCap(), null);
			if(guiProvider != null)
				return guiProvider.getServerGuiElement(guiId, player, world, pos);
		}
		return null;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public Object getClientGuiElement(int guiId, @Nullable EntityPlayer player, @Nullable World world, int x, int y, int z)
	{
		if (player == null || world == null) return null;
		BlockPos pos = new BlockPos(x, y, z);
		TileEntity tile = world.getTileEntity(pos);
		if(tile != null)
		{
			IGuiProvider guiProvider = tile.getCapability(ModCaps.getGuiProviderCap(), null);
			if(guiProvider != null)
				return guiProvider.getClientGuiElement(guiId, player, world, pos);
		}
		return null;
	}
}
