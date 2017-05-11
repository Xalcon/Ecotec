package net.xalcon.ecotec.client.renderer.block;

import net.minecraft.client.resources.IResourceManager;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.ICustomModelLoader;
import net.minecraftforge.client.model.IModel;
import net.xalcon.ecotec.Ecotec;
import net.xalcon.ecotec.common.init.ModBlocks;

public class ModelLoaderBlockPipe implements ICustomModelLoader
{
	@Override
	public boolean accepts(ResourceLocation modelLocation)
	{
		return modelLocation.getResourceDomain().equals(Ecotec.MODID) &&
				modelLocation.getResourcePath().contains("builtin/pipe");
	}

	@Override
	public IModel loadModel(ResourceLocation modelLocation) throws Exception
	{
		return new ModelBlockPipe();
	}

	@Override
	public void onResourceManagerReload(IResourceManager resourceManager) { /* NOP */ }
}
