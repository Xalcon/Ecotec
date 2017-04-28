package net.xalcon.ecotec.common;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.xalcon.ecotec.client.gui.*;
import net.xalcon.ecotec.common.inventories.*;
import net.xalcon.ecotec.common.tileentities.TileEntityBase;
import net.xalcon.ecotec.common.tileentities.agriculture.TileEntityMachineBreeder;
import net.xalcon.ecotec.common.tileentities.agriculture.TileEntityMachineHarvester;
import net.xalcon.ecotec.common.tileentities.agriculture.TileEntityMachinePlanter;
import net.xalcon.ecotec.common.tileentities.agriculture.TileEntityMachineRancher;
import net.xalcon.ecotec.common.tileentities.logistics.TileEntityDeepStorageUnit;
import net.xalcon.ecotec.common.tileentities.machines.TileEntityMachineAutoSpawner;
import net.xalcon.ecotec.common.tileentities.machines.TileEntityMachineGrinder;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@SuppressWarnings("Convert2MethodRef")
public class GuiRegistry
{
	private static int idCounter;
	private static Map<Integer, GuiElementProvider> guiRegistry = new HashMap<>();

	// DO NOT REPLACE LAMBDA WITH METHODREF! THIS WILL CRASH ON A DEDICATED SERVER!
	// The lambda acts as a proxy which allows instantiation of the enum members without actually referencing
	// client side code (Gui is annotated with @Sided.CLIENT)
	public final static int MachineHarvester = register(ContainerMachineHarvester::new, c -> new GuiMachineHarvester(c), TileEntityMachineHarvester.class);
	public final static int MachinePlanter = register(ContainerMachinePlanter::new, c -> new GuiMachinePlanter(c), TileEntityMachinePlanter.class);
	public final static int MachineBreeder = register(ContainerMachineBreeder::new, c -> new GuiMachineBreeder(c), TileEntityMachineBreeder.class);
	public final static int MachineRancher = register(ContainerMachineRancher::new, c -> new GuiMachineRancher(c), TileEntityMachineRancher.class);
	public final static int MachineGrinder = register(ContainerMachineGrinder::new, c -> new GuiMachineGrinder(c), TileEntityMachineGrinder.class);
	public final static int MachineAutoSpawner = register(ContainerMachineAutoSpawner::new, c -> new GuiMachineAutoSpawner(c), TileEntityMachineAutoSpawner.class);
	public final static int DeepStorageUnit = register(ContainerDsu::new, c -> new GuiDeepStorageUnit(c), TileEntityDeepStorageUnit.class);

	public static GuiElementProvider fromId(int guiId) { return guiRegistry.get(guiId); }

	public static <T extends TileEntityBase> int register(Function<GuiElementContext<T>, Object> serverFactory, Function<GuiElementContext<T>, Object> clientFactory, Class<T> tileEntityClass)
	{
		int guiId = idCounter++;
		GuiElementProvider<T> guiElementProvider = new GuiElementProvider<>(serverFactory, clientFactory, tileEntityClass);
		guiRegistry.put(guiId, guiElementProvider);
		return guiId;
	}

	public static class GuiElementProvider<T extends TileEntityBase>
	{
		private Function<GuiElementContext<T>, Object> serverFactory;
		private Function<GuiElementContext<T>, Object> clientFactory;
		private Class<T> tileEntityClass;

		private GuiElementProvider(Function<GuiElementContext<T>, Object> serverFactory, Function<GuiElementContext<T>, Object> clientFactory, Class<T> tileEntityClass)
		{
			this.serverFactory = serverFactory;
			this.clientFactory = clientFactory;
			this.tileEntityClass = tileEntityClass;
		}

		public Object getServerGuiElement(EntityPlayer player, World world, int x, int y, int z)
		{
			return this.serverFactory.apply(new GuiElementContext<>(player, world, x, y, z, this.tileEntityClass));
		}

		@SideOnly(Side.CLIENT)
		public Object getClientGuiElement(EntityPlayer player, World world, int x, int y, int z)
		{
			return this.clientFactory.apply(new GuiElementContext<>(player, world, x, y, z, this.tileEntityClass));
		}
	}
}
