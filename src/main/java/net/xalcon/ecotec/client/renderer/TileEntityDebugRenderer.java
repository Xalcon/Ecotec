package net.xalcon.ecotec.client.renderer;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.VertexBuffer;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.AxisAlignedBB;
import net.xalcon.ecotec.common.tileentities.agriculture.TileEntityMachinePlanter;
import org.lwjgl.opengl.GL11;

public class TileEntityDebugRenderer extends TileEntitySpecialRenderer<TileEntityMachinePlanter>
{
	private static RenderManager render = Minecraft.getMinecraft().getRenderManager();

	@Override
	public void renderTileEntityAt(TileEntityMachinePlanter te, double x, double y, double z, float partialTicks, int destroyStage)
	{
		super.renderTileEntityAt(te, x, y, z, partialTicks, destroyStage);

		int radius = te.getWorkRadius();
		AxisAlignedBB area = new AxisAlignedBB(te.getPos().offset(EnumFacing.UP, 2)).expand(radius, 0, radius);

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
