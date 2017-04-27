package net.xalcon.ecotec.client.gui.widgets;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.VertexBuffer;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraftforge.fml.client.config.GuiUtils;

import java.util.Collections;

public abstract class GuiWidget extends Gui
{
	public abstract void renderWidgetBackground();

	public abstract void renderWidgetForeground();

	public abstract void handleMouseOver(int mouseX, int mouseY);

	public static void drawHoveringText(String text, int x, int y)
	{
		GuiUtils.drawHoveringText(Collections.singletonList(text), x, y,
				256, 256, -1, Minecraft.getMinecraft().fontRenderer);
	}

	public static void drawColoredRectMultiply(int left, int top, int right, int bottom, int color)
	{
		if (left < right)
		{
			int i = left;
			left = right;
			right = i;
		}

		if (top < bottom)
		{
			int j = top;
			top = bottom;
			bottom = j;
		}

		float f3 = (float)(color >> 24 & 255) / 255.0F;
		float f = (float)(color >> 16 & 255) / 255.0F;
		float f1 = (float)(color >> 8 & 255) / 255.0F;
		float f2 = (float)(color & 255) / 255.0F;
		Tessellator tessellator = Tessellator.getInstance();
		VertexBuffer vertexbuffer = tessellator.getBuffer();
		GlStateManager.enableBlend();
		GlStateManager.disableTexture2D();
		GlStateManager.tryBlendFuncSeparate(GlStateManager.SourceFactor.DST_COLOR, GlStateManager.DestFactor.ZERO,
				GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);
		GlStateManager.color(f, f1, f2, f3);
		vertexbuffer.begin(7, DefaultVertexFormats.POSITION);
		vertexbuffer.pos((double)left, (double)bottom, 0.0D).endVertex();
		vertexbuffer.pos((double)right, (double)bottom, 0.0D).endVertex();
		vertexbuffer.pos((double)right, (double)top, 0.0D).endVertex();
		vertexbuffer.pos((double)left, (double)top, 0.0D).endVertex();
		tessellator.draw();
		GlStateManager.enableTexture2D();
		GlStateManager.disableBlend();
	}

	public static void drawGradientMultiply(int left, int top, int width, int height, float zLevel, int startColor, int endColor)
	{
		float f = (float)(startColor >> 24 & 255) / 255.0F;
		float f1 = (float)(startColor >> 16 & 255) / 255.0F;
		float f2 = (float)(startColor >> 8 & 255) / 255.0F;
		float f3 = (float)(startColor & 255) / 255.0F;
		float f4 = (float)(endColor >> 24 & 255) / 255.0F;
		float f5 = (float)(endColor >> 16 & 255) / 255.0F;
		float f6 = (float)(endColor >> 8 & 255) / 255.0F;
		float f7 = (float)(endColor & 255) / 255.0F;
		GlStateManager.disableTexture2D();
		GlStateManager.enableBlend();
		GlStateManager.disableAlpha();
		GlStateManager.tryBlendFuncSeparate(GlStateManager.SourceFactor.DST_COLOR, GlStateManager.DestFactor.ZERO,
				GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);
		GlStateManager.shadeModel(7425);
		Tessellator tessellator = Tessellator.getInstance();
		VertexBuffer vertexbuffer = tessellator.getBuffer();
		vertexbuffer.begin(7, DefaultVertexFormats.POSITION_COLOR);
		vertexbuffer.pos((double)left + width, (double)top, (double)zLevel).color(f1, f2, f3, f).endVertex();
		vertexbuffer.pos((double)left, (double)top, (double)zLevel).color(f1, f2, f3, f).endVertex();
		vertexbuffer.pos((double)left, (double)top + height, (double)zLevel).color(f5, f6, f7, f4).endVertex();
		vertexbuffer.pos((double)left + width, (double)top + height, (double)zLevel).color(f5, f6, f7, f4).endVertex();
		tessellator.draw();
		GlStateManager.shadeModel(7424);
		GlStateManager.disableBlend();
		GlStateManager.enableAlpha();
		GlStateManager.enableTexture2D();
	}
}
