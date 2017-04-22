package net.xalcon.ecotec.integration.vanilla;

import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.*;
import net.xalcon.ecotec.EcotecMod;
import net.xalcon.ecotec.common.EcotecRegistries;
import net.xalcon.ecotec.common.farmables.plantables.PlantableBasic;
import net.xalcon.ecotec.common.farmables.plantables.PlantableCrop;

@Mod(modid = EcotecMod.MODID + "!vanilla_compat", version = EcotecMod.VERSION, dependencies = "after:" + EcotecMod.MODID)
public class VanillaCompat
{
	@Mod.EventHandler
	public static void preInit(FMLPreInitializationEvent event) { }

	@Mod.EventHandler
	public static void init(FMLInitializationEvent event)
	{
		EcotecRegistries.Plantables.register(new PlantableBasic(Blocks.SAPLING));
		EcotecRegistries.Plantables.register(new PlantableBasic(Blocks.CACTUS));
		EcotecRegistries.Plantables.register(new PlantableBasic(Blocks.REEDS, Items.REEDS));
		EcotecRegistries.Plantables.register(new PlantableBasic(Blocks.NETHER_WART, Items.NETHER_WART));

		EcotecRegistries.Plantables.register(new PlantableCrop(Blocks.WHEAT, Items.WHEAT_SEEDS));
		EcotecRegistries.Plantables.register(new PlantableCrop(Blocks.POTATOES, Items.POTATO));
		EcotecRegistries.Plantables.register(new PlantableCrop(Blocks.CARROTS, Items.CARROT));
		EcotecRegistries.Plantables.register(new PlantableCrop(Blocks.BEETROOTS, Items.BEETROOT_SEEDS));
		EcotecRegistries.Plantables.register(new PlantableCrop(Blocks.MELON_STEM, Items.MELON_SEEDS));
		EcotecRegistries.Plantables.register(new PlantableCrop(Blocks.PUMPKIN_STEM, Items.PUMPKIN_SEEDS));
	}

	@Mod.EventHandler
	public static void postInit(FMLPostInitializationEvent event) { }
}
