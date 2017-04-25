package net.xalcon.ecotec.client.gui.widgets;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTank;
import org.lwjgl.util.Rectangle;

public class WidgetFluidGauge extends GuiWidget
{
	private FluidTank fluidTank;
	private Rectangle gaugeRect;

	public WidgetFluidGauge(int posX, int posY, FluidTank fluidTank)
	{
		this.gaugeRect = new Rectangle(posX, posY, 18, 54);
		this.fluidTank = fluidTank;
	}

	@Override
	public void renderWidgetBackground()
	{
		this.drawTexturedModalRect(this.gaugeRect.getX(), this.gaugeRect.getY(), 176 + 18, 0,
				this.gaugeRect.getWidth(), this.gaugeRect.getHeight());
	}

	@Override
	public void renderWidgetForeground()
	{
		FluidStack fluidStack = this.fluidTank.getFluid();
		if (fluidStack == null) return;

		Fluid fluid = fluidStack.getFluid();
		if (fluid == null) return;

		Minecraft mc = Minecraft.getMinecraft();
		mc.getTextureManager().bindTexture(TextureMap.LOCATION_BLOCKS_TEXTURE);

		float fillPercentage = this.fluidTank.getFluidAmount() / (float) this.fluidTank.getCapacity();
		int fluidRenderHeight = (int) (fillPercentage * (this.gaugeRect.getHeight() - 2));
		int fluidRenderOffset = (this.gaugeRect.getHeight() - 2) - fluidRenderHeight;
		int fluidColor = fluid.getColor();
		float alpha = (float) (fluidColor >> 24 & 255) / 255f;
		float red = (fluidColor & 0xFF0000 >> 16) / 255f;
		float green = (fluidColor & 0x00FF00 >> 8) / 255f;
		float blue = (fluidColor & 0xFF) / 255f;
		GlStateManager.color(red, green, blue, alpha);
		GlStateManager.enableBlend();
		GlStateManager.enableAlpha();

		TextureAtlasSprite fluidSprite = mc.getTextureMapBlocks().getTextureExtry(fluid.getStill().toString());
		if (fluidSprite != null)
			this.drawTexturedModalRect(this.gaugeRect.getX() + 1, this.gaugeRect.getY() + 1 + fluidRenderOffset, fluidSprite,
					16,
					fluidRenderHeight);
	}

	@Override
	public void handleMouseOver(int mouseX, int mouseY)
	{
		if (this.gaugeRect.contains(mouseX, mouseY))
		{
			String fluidName = "empty";
			FluidStack fluidStack = this.fluidTank.getFluid();
			if (fluidStack == null) return;

			Fluid fluid = fluidStack.getFluid();
			if (fluid != null) fluidName = fluid.getLocalizedName(fluidStack);

			String toolTip = fluidName + " " + this.fluidTank.getFluidAmount() + " / " + this.fluidTank.getCapacity();
			GuiWidget.drawHoveringText(toolTip, mouseX, mouseY);
		}
	}
}
