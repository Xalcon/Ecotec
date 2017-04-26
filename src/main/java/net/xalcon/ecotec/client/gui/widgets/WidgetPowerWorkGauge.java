package net.xalcon.ecotec.client.gui.widgets;

import net.minecraft.client.gui.Gui;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.resources.I18n;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.energy.CapabilityEnergy;
import net.minecraftforge.energy.IEnergyStorage;
import net.xalcon.ecotec.common.tileentities.TileEntityMachinePowered;
import org.lwjgl.util.Rectangle;

import java.util.function.Supplier;

public class WidgetPowerWorkGauge extends GuiWidget
{
	private final TileEntityMachinePowered tileEntity;
	private Rectangle powerBar;
	private Rectangle workBar;

	public WidgetPowerWorkGauge(int posX, int posY, TileEntityMachinePowered tileEntity)
	{
		this.tileEntity = tileEntity;
		this.powerBar = new Rectangle(posX, posY, 8, 34);
		this.workBar = new Rectangle(posX + 10, posY, 8, 34);
	}

	@Override
	public void renderWidgetBackground()
	{
		this.drawTexturedModalRect(this.powerBar.getX(), this.powerBar.getY(), 176, 0, 18, 34);
		IEnergyStorage energyStorage = this.tileEntity.getCapability(CapabilityEnergy.ENERGY, null);

		float powerPercentage = 0;
		if(energyStorage != null)
			powerPercentage = (float)energyStorage.getEnergyStored() / energyStorage.getMaxEnergyStored();

		int powerBarProgressOffset = 32 - (int) (powerPercentage * 32);
		Gui.drawRect(this.powerBar.getX() + 1, this.powerBar.getY() + 1 + powerBarProgressOffset,
				this.powerBar.getX() + this.powerBar.getWidth() - 1, this.powerBar.getY() + this.powerBar.getHeight() - 1,
				0xFFFF0000);

		float workPercentage = (float)this.tileEntity.getIdleTicks() / this.tileEntity.getMaxIdleTicks();

		int workBarProgressOffset = 32 - (int) (workPercentage * 32);
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
		if (this.powerBar.contains(mouseX, mouseY))
		{
			IEnergyStorage energyStorage = this.tileEntity.getCapability(CapabilityEnergy.ENERGY, null);
			if(energyStorage == null) return;

			String tooltip = I18n.format("tooltip.machine.power_tooltip", energyStorage.getEnergyStored(), energyStorage.getMaxEnergyStored());
			drawHoveringText(tooltip, mouseX, mouseY);
		}
		else if (this.workBar.contains(mouseX, mouseY))
		{
			String tooltip = I18n.format("tooltip.machine.idle_tooltip", this.tileEntity.getIdleTicks(), this.tileEntity.getMaxIdleTicks());
			drawHoveringText(tooltip, mouseX, mouseY);
		}
	}
}
