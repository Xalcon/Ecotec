package net.xalcon.ecotec.client.gui.widgets;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.client.resources.I18n;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTank;
import net.minecraftforge.fml.client.config.GuiUtils;
import net.xalcon.ecotec.Ecotec;
import org.lwjgl.util.Rectangle;

import java.util.Arrays;

public class WidgetFluidGauge extends GuiWidget
{
	protected static final ResourceLocation WIDGET_TEXTURE = new ResourceLocation(Ecotec.MODID, "textures/gui/widgets/fluid_gauge.png");
	private FluidTank fluidTank;
	private Rectangle gaugeRect;
	private boolean useBigGaugeGrid;

	public WidgetFluidGauge(int posX, int posY, FluidTank fluidTank, boolean useBigGaugeGrid)
	{
		this.gaugeRect = new Rectangle(posX, posY, 18, 62);
		this.fluidTank = fluidTank;
		this.useBigGaugeGrid = useBigGaugeGrid;
	}

	@Override
	public void renderWidgetBackground()
	{
		Minecraft mc = Minecraft.getMinecraft();
		mc.getTextureManager().bindTexture(WIDGET_TEXTURE);
		Gui.drawModalRectWithCustomSizedTexture(this.gaugeRect.getX(), this.gaugeRect.getY(), 0, 0,
				this.gaugeRect.getWidth(), this.gaugeRect.getHeight(), 64, 64);
	}

	@Override
	public void renderWidgetForeground()
	{
		Minecraft mc = Minecraft.getMinecraft();
		FluidStack fluidStack = this.fluidTank.getFluid();
		if (fluidStack != null)
		{
			Fluid fluid = fluidStack.getFluid();
			if (fluid != null)
			{
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
		}

		mc.getTextureManager().bindTexture(WIDGET_TEXTURE);
		GlStateManager.color(1, 1, 1, 1);
		Gui.drawModalRectWithCustomSizedTexture(this.gaugeRect.getX() + 1, this.gaugeRect.getY() + 1, this.useBigGaugeGrid ? 32 : 48, 0,
				16, 60, 64, 64);
	}

	@Override
	public void handleMouseOver(int mouseX, int mouseY)
	{
		if (this.gaugeRect.contains(mouseX, mouseY))
		{
			String fluidName = "empty";
			FluidStack fluidStack = this.fluidTank.getFluid();
			if (fluidStack != null)
			{
				Fluid fluid = fluidStack.getFluid();
				if (fluid != null) fluidName = fluid.getLocalizedName(fluidStack);
			}

			String toolTip = I18n.format("gui." + Ecotec.MODID + ".machine.widget_gauge.tooltip", fluidName, this.fluidTank.getFluidAmount(), this.fluidTank.getCapacity());
			GuiUtils.drawHoveringText(Arrays.asList(toolTip.split("\n")), mouseX, mouseY,256, 256, -1, Minecraft.getMinecraft().fontRenderer);
		}
	}
}
