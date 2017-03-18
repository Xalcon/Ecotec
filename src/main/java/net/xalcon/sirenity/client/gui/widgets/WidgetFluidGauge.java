package net.xalcon.sirenity.client.gui.widgets;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BlockFluidRenderer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;
import org.lwjgl.util.Point;
import org.lwjgl.util.Rectangle;

import java.util.function.Supplier;

public class WidgetFluidGauge extends GuiWidget
{
	private final Supplier<FluidData> fluidDataSupplier;
	private Rectangle gaugeRect;

	public WidgetFluidGauge(int posX, int posY, Supplier<FluidData> fluidDataSupplier)
	{
		this.gaugeRect = new Rectangle(posX, posY, 18, 54);
		this.fluidDataSupplier = fluidDataSupplier;
	}

	@Override
	public void renderWidgetBackground()
	{
		this.drawTexturedModalRect(this.gaugeRect.getX(), this.gaugeRect.getY(), 176+18, 0,
			this.gaugeRect.getWidth(), this.gaugeRect.getHeight());
	}

	@Override
	public void renderWidgetForeground(int mouseX, int mouseY)
	{
		Minecraft mc = Minecraft.getMinecraft();
		FluidData fluidData = fluidDataSupplier.get();
		TextureAtlasSprite fluidSprite = mc.getTextureMapBlocks().getTextureExtry(fluidData.fluid.getStill().toString());

		mc.getTextureManager().bindTexture(TextureMap.LOCATION_BLOCKS_TEXTURE);

		int fluidRenderHeight = (int)(fluidData.fillPercentage * (this.gaugeRect.getHeight() - 2));
		int fluidRenderOffset = (this.gaugeRect.getHeight() - 2) - fluidRenderHeight;
		int fluidColor = fluidData.getFluid().getColor();
		GlStateManager.color((fluidColor & 0xFF0000 >> 16) / 255, (fluidColor & 0x00FF00 >> 8) / 255, (fluidColor & 0xFF) / 255);

		this.drawTexturedModalRect(this.gaugeRect.getX() + 1, this.gaugeRect.getY() + 1 + fluidRenderOffset, fluidSprite,
				16,
				fluidRenderHeight);

		if(this.gaugeRect.contains(mouseX, mouseY))
		{
			GuiWidget.drawHoveringText(fluidDataSupplier.get().getToolTip(), mouseX, mouseY);
		}
	}

	public static class FluidData
	{
		private String toolTip;
		private Fluid fluid;
		private float fillPercentage;

		public FluidData(String toolTip, Fluid fluid, float fillPercentage)
		{
			this.toolTip = toolTip;
			this.fluid = fluid;
			this.fillPercentage = fillPercentage;
		}

		public String getToolTip()
		{
			return toolTip;
		}

		public Fluid getFluid()
		{
			return fluid;
		}

		public float getFillPercentage()
		{
			return fillPercentage;
		}
	}
}
