package net.xalcon.ecotec.client.gui.widgets;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.resources.I18n;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.energy.CapabilityEnergy;
import net.minecraftforge.energy.IEnergyStorage;
import net.xalcon.ecotec.Ecotec;
import net.xalcon.ecotec.common.tileentities.TileEntityMachinePowered;
import org.lwjgl.util.Rectangle;

public class WidgetPowerGauge extends GuiWidget
{
	protected static final ResourceLocation WIDGET_TEXTURE = new ResourceLocation(Ecotec.MODID, "textures/gui/widgets/power_gauge.png");
	private final TileEntityMachinePowered tileEntity;
	private Rectangle powerBar;
	private int powerGaugeColor;

	public WidgetPowerGauge(int posX, int posY, TileEntityMachinePowered tileEntity)
	{
		this(posX, posY, tileEntity, 0x000000);
	}

	public WidgetPowerGauge(int posX, int posY, TileEntityMachinePowered tileEntity, int color)
	{
		this.tileEntity = tileEntity;
		this.powerBar = new Rectangle(posX, posY, 14, 42);
		this.powerGaugeColor = color;
	}

	@Override
	public void renderWidgetBackground()
	{
		Minecraft.getMinecraft().getTextureManager().bindTexture(WIDGET_TEXTURE);
		Gui.drawModalRectWithCustomSizedTexture(this.powerBar.getX(), this.powerBar.getY(), 0, 0, 14, 42, 64, 64);

		Minecraft.getMinecraft().getTextureManager().bindTexture(WIDGET_TEXTURE);
		IEnergyStorage energyStorage = this.tileEntity.getCapability(CapabilityEnergy.ENERGY, null);

		float powerPercentage = 0;
		if(energyStorage != null)
			powerPercentage = (float)energyStorage.getEnergyStored() / energyStorage.getMaxEnergyStored();

		int powerBarProgressOffset = 40 - (int) (powerPercentage * 40);

		if(this.powerGaugeColor != 0)
		{
			/*drawColoredRectMultiply(this.powerBar.getX() + 1, this.powerBar.getY() + 1,
				this.powerBar.getX() + this.powerBar.getWidth() - 1, this.powerBar.getY() + this.powerBar.getHeight() - 1,
				this.powerGaugeColor);*/

			drawGradientMultiply(this.powerBar.getX() + 1, this.powerBar.getY() + 1,12, 40,
					this.zLevel, this.powerGaugeColor, this.powerGaugeColor >> 1);
		}
		else
		{
			drawGradientMultiply(this.powerBar.getX() + 1, this.powerBar.getY() + 1,12, 20,
					this.zLevel, 0xFF00FF00, 0xFFFFFF00);
			drawGradientMultiply(this.powerBar.getX() + 1, this.powerBar.getY() + 21,12, 20,
					this.zLevel, 0xFFFFFF00, 0xFFFF0000);
		}

		drawColoredRectMultiply(this.powerBar.getX() + 1, this.powerBar.getY() + 1,
				this.powerBar.getX() + this.powerBar.getWidth() - 1, this.powerBar.getY() + powerBarProgressOffset + 1,
				0xFF555555);

		// drawRect doesn't reset the color, so we need to do it
		GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
	}

	@Override
	public void renderWidgetForeground()
	{

	}

	@Override
	public void handleMouseOver(int mouseX, int mouseY)
	{
		if (this.powerBar.contains(mouseX, mouseY))
		{
			IEnergyStorage energyStorage = this.tileEntity.getCapability(CapabilityEnergy.ENERGY, null);
			if(energyStorage == null) return;

			String tooltip = I18n.format("tooltip.machine.power_tooltip", energyStorage.getEnergyStored(), energyStorage.getMaxEnergyStored());
			drawHoveringText(tooltip, mouseX, mouseY);
		}
	}
}
