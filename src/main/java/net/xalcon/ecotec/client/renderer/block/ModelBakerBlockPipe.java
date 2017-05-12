package net.xalcon.ecotec.client.renderer.block;

import com.google.common.base.Function;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.block.model.*;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.vertex.VertexFormat;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.IModel;
import net.minecraftforge.client.model.IPerspectiveAwareModel;
import net.minecraftforge.common.model.IModelState;
import net.minecraftforge.common.model.TRSRTransformation;
import net.minecraftforge.common.property.IExtendedBlockState;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.xalcon.ecotec.Ecotec;
import net.xalcon.ecotec.common.blocks.logistics.BlockPipe;
import net.xalcon.ecotec.common.blocks.properties.EnumPipeConnection;
import net.xalcon.ecotec.common.tileentities.logistics.TileEntityPipe;
import org.apache.commons.lang3.tuple.Pair;

import javax.annotation.Nullable;
import javax.vecmath.Matrix4f;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@SideOnly(Side.CLIENT)
public class ModelBakerBlockPipe implements IPerspectiveAwareModel
{
	private final IBakedModel center;
	private IBakedModel[][] armModels;


	public ModelBakerBlockPipe(IBakedModel center, IBakedModel[][] armModels)
	{
		this.center = center;
		this.armModels = armModels;
	}

	@Override
	public Pair<? extends IBakedModel, Matrix4f> handlePerspective(ItemCameraTransforms.TransformType cameraTransformType)
	{
		if (this.center instanceof IPerspectiveAwareModel)
		{
			Matrix4f matrix4f = ((IPerspectiveAwareModel)this.center).handlePerspective(cameraTransformType).getRight();
			return Pair.of(this, matrix4f);
		}
		else
		{
			// If the parent model isn't an IPerspectiveAware, we'll need to generate the correct matrix ourselves using the
			//  ItemCameraTransforms.
			ItemCameraTransforms itemCameraTransforms = this.center.getItemCameraTransforms();
			ItemTransformVec3f itemTransformVec3f = itemCameraTransforms.getTransform(cameraTransformType);
			TRSRTransformation tr = new TRSRTransformation(itemTransformVec3f);
			// The TRSRTransformation for vanilla items have blockCenterToCorner() applied, however handlePerspective
			//  reverses it back again with blockCornerToCenter().  So we don't need to apply it here.
			return Pair.of(this, tr.getMatrix());
		}
	}

	@Override
	public List<BakedQuad> getQuads(@Nullable IBlockState state, @Nullable EnumFacing side, long rand)
	{
		List<BakedQuad> quadsList = new ArrayList<>();
		quadsList.addAll(this.center.getQuads(state, side, rand));
		if (!(state instanceof IExtendedBlockState)) return quadsList;

		TileEntityPipe tile = ((IExtendedBlockState)state).getValue(BlockPipe.TILE_ENTITY_PROP);
		if(tile == null) return quadsList;

		for(EnumFacing facing : EnumFacing.values())
		{
			EnumPipeConnection con = tile.getConnection(facing);
			if(con != EnumPipeConnection.DISCONNECTED)
				quadsList.addAll(this.armModels[facing.getIndex()][con.getIndex()].getQuads(state, side, rand));
		}
		return quadsList;
	}

	@Override
	public boolean isAmbientOcclusion()
	{
		return this.center.isAmbientOcclusion();
	}

	@Override
	public boolean isGui3d()
	{
		return this.center.isGui3d();
	}

	@Override
	public boolean isBuiltInRenderer()
	{
		return false;
	}

	@Override
	public TextureAtlasSprite getParticleTexture()
	{
		return Minecraft.getMinecraft().getTextureMapBlocks().getAtlasSprite(Ecotec.MODID + ":pipe");
	}

	@Override
	public ItemCameraTransforms getItemCameraTransforms()
	{
		return this.center.getItemCameraTransforms();
	}

	@Override
	public ItemOverrideList getOverrides()
	{
		return null;
	}
}
