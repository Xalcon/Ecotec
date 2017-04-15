package net.xalcon.minefactory.client.gui.widgets;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraftforge.fml.client.config.GuiUtils;

import java.util.Collections;

public abstract class GuiWidget extends Gui
{
	public abstract void renderWidgetBackground();

	public abstract void renderWidgetForeground();

	public abstract void handleMouseOver(int mouseX, int mouseY);

	protected static void drawHoveringText(String text, int x, int y)
	{
		GuiUtils.drawHoveringText(Collections.singletonList(text), x, y,
				256, 256, -1, Minecraft.getMinecraft().fontRenderer);
	}
}
