package net.xalcon.minefactory;

import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.xalcon.minefactory.common.CommonProxy;
import net.xalcon.minefactory.common.ModBlocks;
import net.xalcon.minefactory.common.ModGuiHandler;
import net.xalcon.minefactory.common.ModTileEntities;
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
    public void preInit(FMLPreInitializationEvent event)
    {
        ModBlocks.init();
        ModTileEntities.init();
        NetworkRegistry.INSTANCE.registerGuiHandler(this, new ModGuiHandler());
    }

    @EventHandler
    public void init(FMLInitializationEvent event)
    {
        Proxy.init();
    }
}
