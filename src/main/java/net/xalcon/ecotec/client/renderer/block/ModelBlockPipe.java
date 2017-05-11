package net.xalcon.ecotec.client.renderer.block;

import com.google.common.base.Function;
import com.google.common.collect.ImmutableList;
import net.minecraft.client.renderer.block.model.IBakedModel;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.vertex.VertexFormat;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.IModel;
import net.minecraftforge.client.model.ModelLoaderRegistry;
import net.minecraftforge.common.model.IModelState;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.xalcon.ecotec.Ecotec;

import java.util.Collection;

@SideOnly(Side.CLIENT)
public class ModelBlockPipe implements IModel
{
	private final static ResourceLocation TEXTURE = new ResourceLocation(Ecotec.MODID, "blocks/fluid_pipe");

	private final static ModelResourceLocation CENTER = new ModelResourceLocation(Ecotec.MODID + ":pipes/pipe_center");
	private final static ModelResourceLocation UP = new ModelResourceLocation(Ecotec.MODID + ":pipes/pipe_up");
	private final static ModelResourceLocation DOWN = new ModelResourceLocation(Ecotec.MODID + ":pipes/pipe_down");
	private final static ModelResourceLocation NORTH = new ModelResourceLocation(Ecotec.MODID + ":pipes/pipe_north");
	private final static ModelResourceLocation SOUTH = new ModelResourceLocation(Ecotec.MODID + ":pipes/pipe_south");
	private final static ModelResourceLocation WEST = new ModelResourceLocation(Ecotec.MODID + ":pipes/pipe_west");
	private final static ModelResourceLocation EAST = new ModelResourceLocation(Ecotec.MODID + ":pipes/pipe_east");

	@Override
	public Collection<ResourceLocation> getDependencies()
	{
		return ImmutableList.of(CENTER, UP, DOWN, NORTH, SOUTH, WEST, EAST);
	}

	@Override
	public Collection<ResourceLocation> getTextures()
	{
		return ImmutableList.of(TEXTURE);
	}

	@Override
	public IBakedModel bake(IModelState state, VertexFormat format, Function<ResourceLocation, TextureAtlasSprite> bakedTextureGetter)
	{
		try
		{
			IBakedModel center = bakeWithOwnState(ModelLoaderRegistry.getModel(CENTER), state, format, bakedTextureGetter);
			IBakedModel up = bakeWithOwnState(ModelLoaderRegistry.getModel(UP), state, format, bakedTextureGetter);
			IBakedModel down = bakeWithOwnState(ModelLoaderRegistry.getModel(DOWN), state, format, bakedTextureGetter);
			IBakedModel north = bakeWithOwnState(ModelLoaderRegistry.getModel(NORTH), state, format, bakedTextureGetter);
			IBakedModel south = bakeWithOwnState(ModelLoaderRegistry.getModel(SOUTH), state, format, bakedTextureGetter);
			IBakedModel west = bakeWithOwnState(ModelLoaderRegistry.getModel(WEST), state, format, bakedTextureGetter);
			IBakedModel east = bakeWithOwnState(ModelLoaderRegistry.getModel(EAST), state, format, bakedTextureGetter);
			return new ModelBakerBlockPipe(center, up, down, north, south, west, east);
		}
		catch (Exception e)
		{
			Ecotec.Log.error("Unable to bake model", e);
		}
		return ModelLoaderRegistry.getMissingModel().bake(state, format, bakedTextureGetter);
	}

	private static IBakedModel bakeWithOwnState(IModel model, IModelState state, VertexFormat format, Function<ResourceLocation, TextureAtlasSprite> bakedTextureGetter)
	{
		// TODO: find out how to apply the parent model state to the submodel state
		return model.bake(model.getDefaultState(), format, bakedTextureGetter);
	}

	@Override
	public IModelState getDefaultState()
	{
		return null;
	}


}
