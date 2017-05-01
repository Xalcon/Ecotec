package net.xalcon.ecotec.client.renderer;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.VertexBuffer;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.AxisAlignedBB;
import net.xalcon.ecotec.api.components.IWorldInteractive;
import net.xalcon.ecotec.common.init.ModCaps;
import org.lwjgl.opengl.GL11;

@SuppressWarnings("unused")
public class TileEntityDebugRenderer<T extends TileEntity> extends TileEntitySpecialRenderer<T>
{
	private static RenderManager render = Minecraft.getMinecraft().getRenderManager();

	@Override
	public void renderTileEntityAt(T te, double x, double y, double z, float partialTicks, int destroyStage)
	{
		super.renderTileEntityAt(te, x, y, z, partialTicks, destroyStage);

		IWorldInteractive worldInteractive = te.getCapability(ModCaps.getWorldInteractiveCap(), null);
		if(worldInteractive == null) return;
		AxisAlignedBB area = worldInteractive.getArea();

		GlStateManager.pushAttrib();
		GlStateManager.pushMatrix();
		GlStateManager.disableLighting();
		GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f);
		GlStateManager.translate(-render.viewerPosX, -render.viewerPosY, -render.viewerPosZ);
		GlStateManager.disableTexture2D();
		GlStateManager.enableAlpha();
		GlStateManager.enableBlend();
		GlStateManager.glPolygonMode(GL11.GL_FRONT_AND_BACK, GL11.GL_LINE);
		renderBox(area);
		GlStateManager.glPolygonMode(GL11.GL_FRONT_AND_BACK, GL11.GL_FILL);
		renderBox(area);

		GlStateManager.enableLighting();
		GlStateManager.enableTexture2D();
		GlStateManager.enableCull();
		GlStateManager.popMatrix();
		GlStateManager.popAttrib();
	}

	private static void renderBox(AxisAlignedBB bb)
	{
		double zFightOffset = 0.005D;
		double minX = bb.minX - zFightOffset;
		double maxX = bb.maxX + zFightOffset;
		double minY = bb.minY + zFightOffset;
		double maxY = bb.maxY - zFightOffset;
		double minZ = bb.minZ - zFightOffset;
		double maxZ = bb.maxZ + zFightOffset;

		Tessellator tess = Tessellator.getInstance();
		VertexBuffer vb = tess.getBuffer();
		vb.begin(GL11.GL_QUADS, DefaultVertexFormats.POSITION_COLOR);
		// NorthFace
		vb.pos(minX, maxY, minZ).color(1.0f, 0.0f, 0.0f, 0.6f).endVertex();
		vb.pos(maxX, maxY, minZ).color(1.0f, 0.0f, 0.0f, 0.6f).endVertex();
		vb.pos(maxX, minY, minZ).color(1.0f, 0.0f, 0.0f, 0.6f).endVertex();
		vb.pos(minX, minY, minZ).color(1.0f, 0.0f, 0.0f, 0.6f).endVertex();
		// SouthFace
		vb.pos(minX, maxY, maxZ).color(1.0f, 0.0f, 0.0f, 0.6f).endVertex();
		vb.pos(minX, minY, maxZ).color(1.0f, 0.0f, 0.0f, 0.6f).endVertex();
		vb.pos(maxX, minY, maxZ).color(1.0f, 0.0f, 0.0f, 0.6f).endVertex();
		vb.pos(maxX, maxY, maxZ).color(1.0f, 0.0f, 0.0f, 0.6f).endVertex();
		// Top Face
		vb.pos(minX, maxY, maxZ).color(1.0f, 0.0f, 0.0f, 0.6f).endVertex();
		vb.pos(maxX, maxY, maxZ).color(1.0f, 0.0f, 0.0f, 0.6f).endVertex();
		vb.pos(maxX, maxY, minZ).color(1.0f, 0.0f, 0.0f, 0.6f).endVertex();
		vb.pos(minX, maxY, minZ).color(1.0f, 0.0f, 0.0f, 0.6f).endVertex();
		// Bottom Face
		vb.pos(minX, minY, maxZ).color(1.0f, 0.0f, 0.0f, 0.6f).endVertex();
		vb.pos(minX, minY, minZ).color(1.0f, 0.0f, 0.0f, 0.6f).endVertex();
		vb.pos(maxX, minY, minZ).color(1.0f, 0.0f, 0.0f, 0.6f).endVertex();
		vb.pos(maxX, minY, maxZ).color(1.0f, 0.0f, 0.0f, 0.6f).endVertex();
		// West Face
		vb.pos(maxX, maxY, minZ).color(1.0f, 0.0f, 0.0f, 0.6f).endVertex();
		vb.pos(maxX, maxY, maxZ).color(1.0f, 0.0f, 0.0f, 0.6f).endVertex();
		vb.pos(maxX, minY, maxZ).color(1.0f, 0.0f, 0.0f, 0.6f).endVertex();
		vb.pos(maxX, minY, minZ).color(1.0f, 0.0f, 0.0f, 0.6f).endVertex();
		// EastFace
		vb.pos(minX, maxY, minZ).color(1.0f, 0.0f, 0.0f, 0.6f).endVertex();
		vb.pos(minX, minY, minZ).color(1.0f, 0.0f, 0.0f, 0.6f).endVertex();
		vb.pos(minX, minY, maxZ).color(1.0f, 0.0f, 0.0f, 0.6f).endVertex();
		vb.pos(minX, maxY, maxZ).color(1.0f, 0.0f, 0.0f, 0.6f).endVertex();
		tess.draw();
	}
}
