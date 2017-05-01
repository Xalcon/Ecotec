package net.xalcon.ecotec.client.gui.widgets;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;

import java.util.function.Supplier;

public class WidgetDynamicString extends GuiWidget
{
	private int posX;
	private int posY;
	private FontRenderer fontRenderer;
	private Supplier<String> stringSupplier;

	public WidgetDynamicString(int posX, int posY, FontRenderer fontRenderer, Supplier<String> stringSupplier)
	{
		this.posX = posX;
		this.posY = posY;
		this.fontRenderer = fontRenderer;
		this.stringSupplier = stringSupplier;
	}

	@Override
	public void renderWidgetBackground()
	{

	}

	@Override
	public void renderWidgetForeground()
	{
		String text = this.stringSupplier.get();
		int textOffset = -(Minecraft.getMinecraft().fontRenderer.getStringWidth(text) >> 1);
		this.fontRenderer.drawString(text, this.posX + textOffset, this.posY, 0xFF404040);
	}

	@Override
	public void handleMouseOver(int mouseX, int mouseY)
	{

	}
}
