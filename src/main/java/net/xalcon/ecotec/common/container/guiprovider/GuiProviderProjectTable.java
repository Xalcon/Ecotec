package net.xalcon.ecotec.common.container.guiprovider;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.SlotCrafting;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.SlotItemHandler;
import net.xalcon.ecotec.api.components.IGuiProvider;
import net.xalcon.ecotec.api.components.wrappers.IContainerSlotHandler;
import net.xalcon.ecotec.api.components.wrappers.IGuiWidgetHandler;
import net.xalcon.ecotec.client.gui.GuiBase;
import net.xalcon.ecotec.common.container.ContainerBase;
import net.xalcon.ecotec.common.container.ContainerEcoCrafting;
import net.xalcon.ecotec.common.container.slots.SlotCraftOuput;
import net.xalcon.ecotec.common.tileentities.logistics.TileEntityProjectTable;

public class GuiProviderProjectTable implements IGuiProvider
{
	private IItemHandler inventory;
	private TileEntityProjectTable tile;

	@Override
	public void initialize(ICapabilityProvider provider)
	{
		this.inventory = provider.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, null);
		if(provider instanceof TileEntityProjectTable)
			this.tile = (TileEntityProjectTable) provider;
	}

	@Override
	public void invalidate()
	{
		this.inventory = null;
		this.tile = null;
	}

	@Override
	public int getContentHeight()
	{
		return 116;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void addWidgets(EntityPlayer player, IGuiWidgetHandler widgetHandler) { }

	@Override
	public void addSlots(EntityPlayer player, IContainerSlotHandler slotHandler)
	{
		if(this.inventory == null || this.tile == null) return;

		// add slots for internal inventory
		for (int y = 0; y < 2; y++)
		{
			for (int x = 0; x < 9; x++)
			{
				slotHandler.addSlot(new SlotItemHandler(this.inventory, x + y * 9, 8 + x * 18, 16 + 3*18 + 6 + y * 18));
			}
		}

		for (int y = 0; y < 3; y++)
		{
			for (int x = 0; x < 3; x++)
			{
				slotHandler.addSlot(new SlotItemHandler(this.tile.getCraftMatrixHander(), x + y * 3, 38 + x * 18, 16 + y * 18));
			}
		}

		slotHandler.addSlot(new SlotCraftOuput(player, this.tile.getCraftMatrixProxy(), this.tile.getCraftOutputHandler(), 0, 120, 16 + 18));
	}

	@Override
	public ContainerBase getServerGuiElement(int guiId, EntityPlayer player, World world, BlockPos pos)
	{
		return new ContainerEcoCrafting(player, world.getTileEntity(pos));
	}

	@Override
	@SideOnly(Side.CLIENT)
	public Object getClientGuiElement(int guiId, EntityPlayer player, World world, BlockPos pos)
	{
		return new GuiBase(new ContainerEcoCrafting(player, world.getTileEntity(pos)), player, world.getTileEntity(pos));
	}
}
