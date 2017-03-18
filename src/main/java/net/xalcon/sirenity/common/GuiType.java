package net.xalcon.sirenity.common;

import net.minecraft.client.gui.Gui;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.xalcon.sirenity.client.gui.GuiMachineHarvester;
import net.xalcon.sirenity.common.container.ContainerMachineHarvester;

import java.util.function.Function;

public enum GuiType
{
	MachineHarvester(ContainerMachineHarvester::new, GuiMachineHarvester::new);

	public static class ContextInfo
	{
		private BlockPos pos;
		private World world;
		private EntityPlayer player;

		public BlockPos getPos()
		{
			return pos;
		}

		public World getWorld()
		{
			return world;
		}

		public EntityPlayer getPlayer()
		{
			return player;
		}

		ContextInfo(EntityPlayer player, World world, int x, int y, int z)
		{
			this.player = player;

			this.world = world;
			this.pos = new BlockPos(x, y, z);
		}
	}

	public Function<ContextInfo, Container> getServerFactory()
	{
		return serverFactory;
	}
	public Function<ContextInfo, Gui> getClientFactory()
	{
		return clientFactory;
	}
	public int getId() { return this.ordinal(); }

	public static GuiType fromId(int guiId)	{ return GuiType.values()[guiId]; }

	private Function<ContextInfo, Container> serverFactory;
	private Function<ContextInfo, Gui> clientFactory;

	GuiType(Function<ContextInfo, Container> serverFactory, Function<ContextInfo, Gui> clientFactory)
	{
		this.serverFactory = serverFactory;
		this.clientFactory = clientFactory;
	}
}
