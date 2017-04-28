package net.xalcon.ecotec.client.gui;

import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.util.ResourceLocation;
import net.xalcon.ecotec.Ecotec;
import net.xalcon.ecotec.client.gui.widgets.GuiWidget;
import net.xalcon.ecotec.common.inventories.ContainerBase;
import net.xalcon.ecotec.common.inventories.GuiElementContext;
import net.xalcon.ecotec.common.tileentities.TileEntityBase;

import java.util.ArrayList;

public abstract class GuiBase<T extends TileEntityBase> extends GuiContainer
{
	protected static final ResourceLocation GUI_TEXTURE = new ResourceLocation(Ecotec.MODID, "textures/gui/gui_base.png");

	private static final int PLAYER_INVENTORY_WIDTH = 18*9;
	private static final int PLAYER_INVENTORY_HEIGHT = 18*4+4;
	private static final int GUI_BORDER_WIDTH = 7;

	protected final IInventory playerInventory;
	protected final T tileEntity;
	private final ContainerBase container;
	protected ArrayList<GuiWidget> widgets = new ArrayList<>();

	public GuiBase(ContainerBase<T> inventorySlotsIn, GuiElementContext<T> context)
	{
		super(inventorySlotsIn);
		this.playerInventory = context.getPlayer().inventory;
		this.tileEntity =  context.getTileEntity();
		this.container = inventorySlotsIn;

		this.xSize = PLAYER_INVENTORY_WIDTH + 2 * GUI_BORDER_WIDTH;
		this.ySize = GUI_BORDER_WIDTH * 2 + PLAYER_INVENTORY_HEIGHT + this.container.getContainerContentHeight();
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

		for (GuiWidget widget : this.widgets)
		{
			widget.renderWidgetForeground();
		}

		for (GuiWidget widget : this.widgets)
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
		this.drawTexturedModalRect(i, j, 0, 0, PLAYER_INVENTORY_WIDTH + 7 * 2, this.container.getContainerContentHeight() + PLAYER_INVENTORY_HEIGHT + 7);
		this.drawTexturedModalRect(i, j + this.container.getContainerContentHeight() + PLAYER_INVENTORY_HEIGHT + 7, 0, 256 - 7, PLAYER_INVENTORY_WIDTH + 7 * 2, 7);
		GlStateManager.pushMatrix();
		GlStateManager.translate(this.guiLeft, this.guiTop, 0);

		for (Slot slot : this.container.inventorySlots)
		{
			this.drawTexturedModalRect(slot.xPos - 1, slot.yPos - 1, 176, 36 + 18, 18, 18);
		}

		for (GuiWidget widget : this.widgets)
		{
			widget.renderWidgetBackground();
		}

		GlStateManager.popMatrix();
	}
}
