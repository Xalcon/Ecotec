package net.xalcon.ecotec.common.components;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.xalcon.ecotec.api.IGuiProvider;
import net.xalcon.ecotec.common.init.ModCaps;

import javax.annotation.Nullable;

public abstract class ComponentGuiProvider implements IGuiProvider
{
	@Override
	public abstract Container getServerGuiElement(int guiId, @Nullable EntityPlayer player, @Nullable World world, BlockPos pos);

	@Override
	@SideOnly(Side.CLIENT)
	public abstract Object getClientGuiElement(int guiId, @Nullable EntityPlayer player, @Nullable World world, BlockPos pos);

	@Override
	public Capability<IGuiProvider> getCapability()
	{
		return ModCaps.getGuiProviderCap();
	}
}
