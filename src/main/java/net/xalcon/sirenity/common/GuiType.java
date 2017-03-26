package net.xalcon.sirenity.common;

import net.minecraft.client.gui.Gui;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.xalcon.sirenity.client.gui.GuiMachineBreeder;
import net.xalcon.sirenity.client.gui.GuiMachineHarvester;
import net.xalcon.sirenity.common.container.ContainerMachineBreeder;
import net.xalcon.sirenity.common.container.ContainerMachineHarvester;

import java.util.function.Function;


@SuppressWarnings("Convert2MethodRef")
public enum GuiType
{
	// DO NOT REPLACE LAMBDA WITH METHODREF! THIS WILL CRASH ON A DEDICATED SERVER!
	// The lambda acts as a proxy which allows instantiation of the enum members without actually referencing
	// client side code (GuiContainer is annotated with @Sided.CLIENT)
	// TODO: find a less ugly version to map gui types without causing sidedness issues
	MachineHarvester(ContainerMachineHarvester::new, c -> new GuiMachineHarvester(c)),
	MachineBreeder(ContainerMachineBreeder::new, c -> new GuiMachineBreeder(c));

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

	public Function<ContextInfo, Object> getServerFactory()
	{
		return serverFactory;
	}
	@SideOnly(Side.CLIENT)
	public Function<ContextInfo, Object> getClientFactory()
	{
		return clientFactory;
	}
	public int getId() { return this.ordinal(); }

	public static GuiType fromId(int guiId)	{ return GuiType.values()[guiId]; }

	private Function<ContextInfo, Object> serverFactory;
	private Function<ContextInfo, Object> clientFactory;

	GuiType(Function<ContextInfo, Object> serverFactory, Function<ContextInfo, Object> clientFactory)
	{
		this.serverFactory = serverFactory;
		this.clientFactory = clientFactory;
	}
}
