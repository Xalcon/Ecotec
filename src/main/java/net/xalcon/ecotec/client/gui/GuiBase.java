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
import net.xalcon.ecotec.api.components.IGuiProvider;
import net.xalcon.ecotec.client.gui.widgets.GuiWidget;
import net.xalcon.ecotec.client.gui.widgets.WidgetPowerGauge;
import net.xalcon.ecotec.common.container.ContainerBase;
import net.xalcon.ecotec.common.container.slots.SlotCraftOuput;
import net.xalcon.ecotec.common.init.ModCaps;

import java.util.ArrayList;
import java.util.List;
import java.util.function.BiFunction;
import java.util.stream.Collectors;

public class GuiBase extends GuiContainer
{
	protected static final ResourceLocation GUI_TEXTURE = new ResourceLocation(Ecotec.MODID, "textures/gui/gui_base.png");

	private static final int PLAYER_INVENTORY_WIDTH = 18*9;
	private static final int PLAYER_INVENTORY_HEIGHT = 18*4+4;
	private static final int GUI_BORDER_WIDTH = 7;

	protected final IInventory playerInventory;
	protected final TileEntity tileEntity;
	private final ContainerBase container;
	private final IBlockState blockState;
	private IGuiProvider guiProvider;
	protected ArrayList<GuiWidget> widgets = new ArrayList<>();

	private List<Runnable> slotRenderList;

	/*public GuiBase(EntityPlayer player, TileEntity tileEntity, BiFunction<EntityPlayer, TileEntity, ContainerBase> containerFactory)
	{
		this(containerFactory.apply(player, tileEntity), player, tileEntity);
	}*/

	public GuiBase(ContainerBase inventorySlotsIn, EntityPlayer player, TileEntity tileEntity)
	{
		super(inventorySlotsIn);
		this.playerInventory = player.inventory;
		this.tileEntity =  tileEntity;
		this.container = inventorySlotsIn;
		this.blockState = tileEntity.getWorld().getBlockState(tileEntity.getPos());
		this.guiProvider = tileEntity.getCapability(ModCaps.getGuiProviderCap(), null);

		this.xSize = PLAYER_INVENTORY_WIDTH + 2 * GUI_BORDER_WIDTH;
		this.ySize = GUI_BORDER_WIDTH * 2 + PLAYER_INVENTORY_HEIGHT + this.guiProvider.getContentHeight();

		if(this.tileEntity.hasCapability(CapabilityEnergy.ENERGY, null))
			this.widgets.add(new WidgetPowerGauge(7, 16, this.tileEntity.getCapability(CapabilityEnergy.ENERGY, null)));

		this.guiProvider.addWidgets(player, this.widgets::add);

		this.slotRenderList = this.container.inventorySlots.stream()
				.<Runnable>map(s -> s instanceof SlotCraftOuput ? () -> this.renderItemSlotBig(s.xPos, s.yPos) : () -> this.renderItemSlot(s.xPos, s.yPos))
				.collect(Collectors.toList());
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

		this.widgets.forEach(GuiWidget::renderWidgetForeground);
		this.widgets.forEach(w -> w.handleMouseOver(relMouseX, relMouseY));
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
		this.drawTexturedModalRect(i, j, 0, 0, PLAYER_INVENTORY_WIDTH + 7 * 2, this.guiProvider.getContentHeight() + PLAYER_INVENTORY_HEIGHT + 7);
		this.drawTexturedModalRect(i, j + this.guiProvider.getContentHeight() + PLAYER_INVENTORY_HEIGHT + 7, 0, 256 - 7, PLAYER_INVENTORY_WIDTH + 7 * 2, 7);
		GlStateManager.pushMatrix();
		GlStateManager.translate(this.guiLeft, this.guiTop, 0);

		this.slotRenderList.forEach(Runnable::run);
		this.widgets.forEach(GuiWidget::renderWidgetBackground);
		GlStateManager.popMatrix();
	}

	private void renderItemSlot(int x, int y)
	{
		this.drawTexturedModalRect(x - 1, y - 1, 176, 0, 18, 18);
	}

	private void renderItemSlotBig(int x, int y)
	{
		this.drawTexturedModalRect(x - 5, y - 5, 176, 18, 26, 26);
	}
}
