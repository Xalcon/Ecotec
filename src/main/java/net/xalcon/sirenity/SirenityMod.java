package net.xalcon.sirenity;

import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.xalcon.sirenity.common.CommonProxy;
import net.xalcon.sirenity.common.ModBlocks;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Mod(modid = SirenityMod.MODID, version = SirenityMod.VERSION)
public class SirenityMod
{
    public static final String MODID = "sirenity";
    public static final String VERSION = "@VERSION@";
    public static final Logger Log = LogManager.getLogger(MODID);

    @SidedProxy(clientSide = "net.xalcon.sirenity.client.ClientProxy", serverSide = "net.xalcon.sirenity.common.CommonProxy")
    public static CommonProxy Proxy;

    @Mod.Instance
    public static SirenityMod instance;

    @EventHandler
    public void preInit(FMLPreInitializationEvent event)
    {
        ModBlocks.init();
    }
}
