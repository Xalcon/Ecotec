package net.xalcon.sirenity.client;

import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraftforge.client.model.ModelLoader;
import net.xalcon.sirenity.SirenityMod;
import net.xalcon.sirenity.common.CommonProxy;

public class ClientProxy extends CommonProxy
{
	@Override
	public void registerItemRenderer(Item item, int meta, String registryName, String variantName)
	{
		ModelLoader.setCustomModelResourceLocation(item, meta, new ModelResourceLocation(SirenityMod.MODID + ":" + registryName, variantName));
	}
}
