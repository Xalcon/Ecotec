package net.xalcon.minefactory.client.gui;

import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.util.ResourceLocation;
import net.xalcon.minefactory.MinefactoryMod;
import net.xalcon.minefactory.client.gui.widgets.GuiWidget;
import net.xalcon.minefactory.common.GuiType;
import net.xalcon.minefactory.common.tileentities.TileEntityBase;

import java.util.ArrayList;

public abstract class GuiBase extends GuiContainer
{
	protected static final ResourceLocation GUI_TEXTURE = new ResourceLocation(MinefactoryMod.MODID, "textures/gui/gui_base.png");
	protected final IInventory playerInventory;
	protected final TileEntityBase tileEntity;
	private final Container container;
	protected ArrayList<GuiWidget> widgets = new ArrayList<>();

	public GuiBase(Container inventorySlotsIn, GuiType.ContextInfo context)
	{
		super(inventorySlotsIn);
		this.playerInventory = context.getPlayer().inventory;
		this.tileEntity = (TileEntityBase) context.getWorld().getTileEntity(context.getPos());
		this.container = inventorySlotsIn;
	}

	/**
	 * Draw the foreground layer for the GuiContainer (everything in front of the items)
	 */
	protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY)
	{
		this.fontRenderer.drawString(this.tileEntity.getDisplayName().getUnformattedText(), 8, 6, 0x404040);
		this.fontRenderer.drawString(this.playerInventory.getDisplayName().getUnformattedText(), 8, this.ySize - 96 + 3, 0x404040);

		int relMouseX = mouseX - this.guiLeft;
		int relMouseY = mouseY - this.guiTop;

		for(GuiWidget widget : this.widgets)
		{
			this.mc.getTextureManager().bindTexture(GUI_TEXTURE);
			widget.renderWidgetForeground();
		}

		for(GuiWidget widget : this.widgets)
		{
			widget.handleMouseOver(relMouseX, relMouseY);
		}
	}

	/**
	 * Draws the background layer of this inventory (behind the items).
	 */
	protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY)
	{
		GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
		this.mc.getTextureManager().bindTexture(GUI_TEXTURE);
		int i = (this.width - this.xSize) / 2;
		int j = (this.height - this.ySize) / 2;
		this.drawTexturedModalRect(i, j, 0, 0, this.xSize, this.ySize);

		GlStateManager.pushMatrix();
		GlStateManager.translate(this.guiLeft, this.guiTop, 0);

		for(Slot slot : this.container.inventorySlots)
		{
			this.drawTexturedModalRect(slot.xPos - 1, slot.yPos - 1, 176, 36 + 18, 18, 18);
		}

		for(GuiWidget widget : this.widgets)
		{
			widget.renderWidgetBackground();
		}

		GlStateManager.popMatrix();
	}
}
