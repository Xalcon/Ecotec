package net.xalcon.ecotec.integration.vanilla;

import net.minecraft.block.*;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.*;
import net.xalcon.ecotec.EcotecMod;
import net.xalcon.ecotec.EcotecRegistries;
import net.xalcon.ecotec.api.EnumHarvestType;
import net.xalcon.ecotec.common.farmables.harvestable.HarvestableAgingCrop;
import net.xalcon.ecotec.common.farmables.harvestable.HarvestableBasic;
import net.xalcon.ecotec.common.farmables.harvestable.HarvestableState;
import net.xalcon.ecotec.common.farmables.plantables.PlantableBasic;
import net.xalcon.ecotec.common.farmables.plantables.PlantableCrop;
import net.xalcon.ecotec.integration.vanilla.rancher.EntityRancherCowLogic;
import net.xalcon.ecotec.integration.vanilla.rancher.EntityRancherMooshroomLogic;
import net.xalcon.ecotec.integration.vanilla.rancher.EntityRancherSheepLogic;
import net.xalcon.ecotec.integration.vanilla.rancher.EntityRancherSquidLogic;

@Mod(modid = EcotecMod.MODID + "!vanilla_compat", version = EcotecMod.VERSION, dependencies = "after:" + EcotecMod.MODID)
public class VanillaCompat
{
	@Mod.EventHandler
	public static void preInit(FMLPreInitializationEvent event) { }

	@Mod.EventHandler
	public static void init(FMLInitializationEvent event)
	{
		/* **********************************************
		 * Plantables
		 * **********************************************/
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

		/* **********************************************
		 * Plantables
		 * **********************************************/
		EcotecRegistries.Harvestables.register(new HarvestableAgingCrop((BlockCrops) Blocks.WHEAT));
		EcotecRegistries.Harvestables.register(new HarvestableAgingCrop((BlockPotato) Blocks.POTATOES));
		EcotecRegistries.Harvestables.register(new HarvestableAgingCrop((BlockCarrot) Blocks.CARROTS));
		EcotecRegistries.Harvestables.register(new HarvestableAgingCrop((BlockBeetroot) Blocks.BEETROOTS));

		EcotecRegistries.Harvestables.register(new HarvestableState<>(Blocks.NETHER_WART, BlockNetherWart.AGE, 3));
		EcotecRegistries.Harvestables.register(new HarvestableState<>(Blocks.COCOA, BlockCocoa.AGE, 2));

		EcotecRegistries.Harvestables.register(new HarvestableBasic(Blocks.REEDS, EnumHarvestType.ColumnKeepBottom));
		EcotecRegistries.Harvestables.register(new HarvestableBasic(Blocks.CACTUS, EnumHarvestType.ColumnKeepBottom));

		EcotecRegistries.Harvestables.register(new HarvestableBasic(Blocks.LOG, EnumHarvestType.Tree));
		EcotecRegistries.Harvestables.register(new HarvestableBasic(Blocks.LOG2, EnumHarvestType.Tree));
		EcotecRegistries.Harvestables.register(new HarvestableBasic(Blocks.MELON_BLOCK));
		EcotecRegistries.Harvestables.register(new HarvestableBasic(Blocks.PUMPKIN));

		/* **********************************************
		 * Ranchables
		 * **********************************************/
		EcotecRegistries.Ranchables.register(new EntityRancherCowLogic());
		EcotecRegistries.Ranchables.register(new EntityRancherMooshroomLogic());
		EcotecRegistries.Ranchables.register(new EntityRancherSheepLogic());
		EcotecRegistries.Ranchables.register(new EntityRancherSquidLogic());
	}

	@Mod.EventHandler
	public static void postInit(FMLPostInitializationEvent event) { }
}
