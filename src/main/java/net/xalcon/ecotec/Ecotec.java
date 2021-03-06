package net.xalcon.ecotec;

import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLConstructionEvent;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.xalcon.ecotec.common.CommonProxy;
import net.xalcon.ecotec.common.handler.BucketEventHandler;
import net.xalcon.ecotec.common.handler.ModGuiHandler;
import net.xalcon.ecotec.common.init.ModCaps;
import net.xalcon.ecotec.common.network.EcotecNetwork;
import net.xalcon.ecotec.common.util.RecipeLoader;
import net.xalcon.ecotec.common.world.gen.WorldGenRubberTreeSmall;
import org.apache.logging.log4j.Logger;

import java.io.File;

@Mod(modid = Ecotec.MODID, version = Ecotec.VERSION)
public class Ecotec
{
	public static final String MODID = "ecotec";
	public static final String VERSION = "@VERSION@";
	public static Logger Log;

	@SidedProxy(clientSide = "net.xalcon.ecotec.client.ClientProxy", serverSide = "net.xalcon.ecotec.common.CommonProxy")
	public static CommonProxy Proxy;

	@Mod.Instance
	public static Ecotec instance;

	@EventHandler
	public void construction(FMLConstructionEvent event)
	{
		// Use forge universal bucket
		FluidRegistry.enableUniversalBucket();
	}

	@EventHandler
	public void preInit(FMLPreInitializationEvent event)
	{
		Log = event.getModLog();
		Proxy.preInit(event);
		EcotecNetwork.initNetwork();
		ModCaps.init();

		RecipeLoader.registerOreDictNames();
		RecipeLoader.initRecipes();
	}

	@EventHandler
	public void init(FMLInitializationEvent event)
	{
		NetworkRegistry.INSTANCE.registerGuiHandler(this, new ModGuiHandler());
		MinecraftForge.EVENT_BUS.register(new BucketEventHandler());
		Proxy.init(event);

		GameRegistry.registerWorldGenerator(new WorldGenRubberTreeSmall(), 10);

	}

	@EventHandler
	public void postInit(FMLPostInitializationEvent event)
	{
		Proxy.postInit(event);
	}
}
