package net.xalcon.minefactory.client.gui.widgets;

import net.minecraft.client.gui.Gui;
import net.minecraft.client.renderer.GlStateManager;
import org.lwjgl.util.Rectangle;

import java.util.function.Supplier;

public class WidgetPowerWorkGauge extends GuiWidget
{
	public static class BarData
	{
		private String tooltip;
		private float progressPercentage;

		public BarData(String tooltip, float progressPercentage)
		{
			this.tooltip = tooltip;
			this.progressPercentage = progressPercentage;
		}

		public String getTooltip()
		{
			return tooltip;
		}

		public float getProgressPercentage()
		{
			return progressPercentage;
		}
	}

	private final Supplier<BarData> powerDataSupplier;
	private final Supplier<BarData> workDataSupplier;

	private Rectangle powerBar;
	private Rectangle workBar;

	public WidgetPowerWorkGauge(int posX, int posY, Supplier<BarData> powerInfoSupplier, Supplier<BarData> workDataSupplier)
	{
		this.powerDataSupplier = powerInfoSupplier;
		this.workDataSupplier = workDataSupplier;
		this.powerBar = new Rectangle(posX, posY, 8, 34);
		this.workBar = new Rectangle(posX + 10, posY, 8, 34);
	}

	@Override
	public void renderWidgetBackground()
	{
		this.drawTexturedModalRect(this.powerBar.getX(), this.powerBar.getY(), 176, 0, 18, 34);
		BarData powerData = this.powerDataSupplier.get();
		int powerBarProgressOffset = 32 - (int) (powerData.getProgressPercentage() * 32);
		Gui.drawRect(this.powerBar.getX() + 1, this.powerBar.getY() + 1 + powerBarProgressOffset,
				this.powerBar.getX() + this.powerBar.getWidth() - 1, this.powerBar.getY() + this.powerBar.getHeight() - 1,
				0xFFFF0000);

		BarData workData = this.workDataSupplier.get();
		int workBarProgressOffset = 32 - (int) (workData.getProgressPercentage() * 32);
		Gui.drawRect(this.workBar.getX() + 1, this.workBar.getY() + 1 + workBarProgressOffset,
				this.workBar.getX() + this.workBar.getWidth() - 1, this.workBar.getY() + this.workBar.getHeight() - 1,
				0xFF00FF00);

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
		if(this.powerBar.contains(mouseX, mouseY))
		{
			drawHoveringText(this.powerDataSupplier.get().getTooltip(), mouseX, mouseY);
		}
		else if(this.workBar.contains(mouseX, mouseY))
		{
			drawHoveringText(this.workDataSupplier.get().getTooltip(), mouseX, mouseY);
		}
	}
}
