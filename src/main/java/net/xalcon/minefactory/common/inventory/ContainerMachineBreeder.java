package net.xalcon.minefactory.common.inventory;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.SlotItemHandler;
import net.xalcon.minefactory.common.GuiType;
import net.xalcon.minefactory.common.tileentities.machines.TileEntityMachineBreeder;

public class ContainerMachineBreeder extends ContainerBase<TileEntityMachineBreeder>
{
	public ContainerMachineBreeder(GuiType.ContextInfo context)
	{
		super(context);

		IItemHandler itemHandler = this.tileEntity.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, null);

		for(int y = 0; y < 3; y++)
		{
			for (int x = 0; x < 3; x++)
			{
				this.addSlotToContainer(new SlotItemHandler(itemHandler, x + y * 3, 61 + x * 18, 16 + y * 18));
			}
		}
	}
}
