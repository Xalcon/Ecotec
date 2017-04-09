package net.xalcon.minefactory;

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
import net.xalcon.minefactory.common.CommonProxy;
import net.xalcon.minefactory.common.init.ModBlocks;
import net.xalcon.minefactory.common.handler.BucketEventHandler;
import net.xalcon.minefactory.common.handler.ModGuiHandler;
import net.xalcon.minefactory.common.init.ModFluids;
import net.xalcon.minefactory.common.init.ModItems;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Mod(modid = MinefactoryMod.MODID, version = MinefactoryMod.VERSION)
public class MinefactoryMod
{
    public static final String MODID = "minefactory";
    public static final String VERSION = "@VERSION@";
    public static final Logger Log = LogManager.getLogger(MODID);

    @SidedProxy(clientSide = "net.xalcon.minefactory.client.ClientProxy", serverSide = "net.xalcon.minefactory.common.CommonProxy")
    public static CommonProxy Proxy;

    @Mod.Instance
    public static MinefactoryMod instance;

    @EventHandler
    public void construction(FMLConstructionEvent event)
    {
        // Use forge universal bucket
        FluidRegistry.enableUniversalBucket();
    }

    @EventHandler
    public void preInit(FMLPreInitializationEvent event)
    {
        ModBlocks.init();
        ModFluids.init();
        ModItems.init();
    }

    @EventHandler
    public void init(FMLInitializationEvent event)
    {
        NetworkRegistry.INSTANCE.registerGuiHandler(this, new ModGuiHandler());
        MinecraftForge.EVENT_BUS.register(new BucketEventHandler());

        Proxy.init();
    }

    @EventHandler
    public void postInit(FMLPostInitializationEvent event)
    {
        Proxy.postInit();
    }
}
