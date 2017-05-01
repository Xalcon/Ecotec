package net.xalcon.ecotec.common.tileentities.logistics;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.xalcon.ecotec.api.IGuiProvider;
import net.xalcon.ecotec.client.gui.GuiDeepStorageUnit;
import net.xalcon.ecotec.common.components.ComponentGuiProvider;
import net.xalcon.ecotec.common.components.ComponentItemHandlerDSU;
import net.xalcon.ecotec.common.inventories.ContainerDsu;
import net.xalcon.ecotec.common.tileentities.TileEntityBase;

import javax.annotation.Nullable;

public class TileEntityDeepStorageUnit extends TileEntityBase
{
	private final IGuiProvider guiProvider = new ComponentGuiProvider() {
		@Override
		public Container getServerGuiElement(int guiId, @Nullable EntityPlayer player, @Nullable World world, BlockPos pos)
		{
			if(player == null || world == null) return null;
			TileEntity tile = world.getTileEntity(pos);
			if(!(tile instanceof TileEntityDeepStorageUnit)) return null;
			return new ContainerDsu(player, (TileEntityDeepStorageUnit) world.getTileEntity(pos));
		}

		@Override
		@SideOnly(Side.CLIENT)
		public Object getClientGuiElement(int guiId, @Nullable EntityPlayer player, @Nullable World world, BlockPos pos)
		{
			if(player == null || world == null) return null;
			TileEntity tile = world.getTileEntity(pos);
			if(!(tile instanceof TileEntityDeepStorageUnit)) return null;
			return new GuiDeepStorageUnit(player, (TileEntityDeepStorageUnit) world.getTileEntity(pos));
		}
	};

	public TileEntityDeepStorageUnit()
	{
		this.addComponent(new ComponentItemHandlerDSU());
		this.addComponent(this.guiProvider);
	}
}
