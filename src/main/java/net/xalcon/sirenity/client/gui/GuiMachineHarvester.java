package net.xalcon.sirenity.client.gui;

import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.inventory.IInventory;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fluids.FluidRegistry;
import net.xalcon.sirenity.SirenityMod;
import net.xalcon.sirenity.client.gui.widgets.GuiWidget;
import net.xalcon.sirenity.client.gui.widgets.WidgetFluidGauge;
import net.xalcon.sirenity.client.gui.widgets.WidgetPowerWorkGauge;
import net.xalcon.sirenity.common.GuiType;
import net.xalcon.sirenity.common.container.ContainerMachineHarvester;

import java.util.ArrayList;

public class GuiMachineHarvester extends GuiContainer
{
	private static final ResourceLocation GUI_TEXTURE = new ResourceLocation(SirenityMod.MODID, "textures/gui/gui_base.png");
	private final IInventory playerInventory;
	private final IInventory machineInventory;

	private ArrayList<GuiWidget> widgets;

	public GuiMachineHarvester(GuiType.ContextInfo context)
	{
		super(new ContainerMachineHarvester(context));
		this.playerInventory = context.getPlayer().inventory;
		this.machineInventory = (IInventory) context.getWorld().getTileEntity(context.getPos());

		this.widgets = new ArrayList<>();

		this.widgets.add(new WidgetPowerWorkGauge(7, 16,
				() -> new WidgetPowerWorkGauge.BarData("Power", 0.5f),
				() -> new WidgetPowerWorkGauge.BarData("Work", 0.5f)));

		this.widgets.add(new WidgetFluidGauge(176 - 18 - 7, 16,
				() -> new WidgetFluidGauge.FluidData("Fluidity", FluidRegistry.getFluid("water"), 0.4f)));
	}

	/**
	 * Draw the foreground layer for the GuiContainer (everything in front of the items)
	 */
	protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY)
	{
		this.fontRendererObj.drawString(this.machineInventory.getDisplayName().getUnformattedText(), 8, 6, 0x404040);
		this.fontRendererObj.drawString(this.playerInventory.getDisplayName().getUnformattedText(), 8, this.ySize - 96 + 3, 0x404040);

		int relMouseX = mouseX - this.guiLeft;
		int relMouseY = mouseY - this.guiTop;

		for(GuiWidget widget : this.widgets)
		{
			this.mc.getTextureManager().bindTexture(GUI_TEXTURE);
			widget.renderWidgetForeground(relMouseX, relMouseY);
		}
	}

	/**
	 * Draws the background layer of this container (behind the items).
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

		for(GuiWidget widget : this.widgets)
		{
			widget.renderWidgetBackground();
		}

		// draw the upgrade slot
		this.drawTexturedModalRect(7, 53, 176, 36, 18, 18);

		GlStateManager.popMatrix();
	}
}
