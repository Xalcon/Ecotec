package net.xalcon.ecotec.client.gui;

import net.minecraft.block.state.IBlockState;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.energy.CapabilityEnergy;
import net.xalcon.ecotec.Ecotec;
import net.xalcon.ecotec.client.gui.widgets.GuiWidget;
import net.xalcon.ecotec.client.gui.widgets.WidgetPowerGauge;
import net.xalcon.ecotec.common.inventories.ContainerBase;
import net.xalcon.ecotec.common.inventories.GuiElementContext;

import java.util.ArrayList;
import java.util.function.BiFunction;

public class GuiBase<T extends TileEntity> extends GuiContainer
{
	protected static final ResourceLocation GUI_TEXTURE = new ResourceLocation(Ecotec.MODID, "textures/gui/gui_base.png");

	private static final int PLAYER_INVENTORY_WIDTH = 18*9;
	private static final int PLAYER_INVENTORY_HEIGHT = 18*4+4;
	private static final int GUI_BORDER_WIDTH = 7;

	protected final IInventory playerInventory;
	protected final T tileEntity;
	private final ContainerBase container;
	private final IBlockState blockState;
	protected ArrayList<GuiWidget> widgets = new ArrayList<>();

	public GuiBase(ContainerBase<T> inventorySlotsIn, GuiElementContext<T> context)
	{
		this(inventorySlotsIn, context.getPlayer().inventory, context.getTileEntity());
	}

	public GuiBase(EntityPlayer player, T tileEntity, BiFunction<EntityPlayer, T, ContainerBase<T>> containerFactory)
	{
		this(containerFactory.apply(player, tileEntity), player.inventory, tileEntity);
	}

	public GuiBase(ContainerBase<T> inventorySlotsIn, InventoryPlayer playerInventory, T tileEntity)
	{
		super(inventorySlotsIn);
		this.playerInventory = playerInventory;
		this.tileEntity =  tileEntity;
		this.container = inventorySlotsIn;
		this.blockState = tileEntity.getWorld().getBlockState(tileEntity.getPos());

		this.xSize = PLAYER_INVENTORY_WIDTH + 2 * GUI_BORDER_WIDTH;
		this.ySize = GUI_BORDER_WIDTH * 2 + PLAYER_INVENTORY_HEIGHT + this.container.getContainerContentHeight();

		if(this.tileEntity.hasCapability(CapabilityEnergy.ENERGY, null))
		{
			this.widgets.add(new WidgetPowerGauge(7, 16, this.tileEntity.getCapability(CapabilityEnergy.ENERGY, null)));
		}
	}

	/**
	 * Draw the foreground layer for the GuiContainer (everything in front of the items)
	 */
	protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY)
	{
		this.fontRenderer.drawString(this.blockState.getBlock().getLocalizedName(), 8, 6, 0x404040);
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
