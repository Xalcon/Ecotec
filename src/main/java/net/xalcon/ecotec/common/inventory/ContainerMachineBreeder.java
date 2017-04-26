package net.xalcon.ecotec.common.inventory;

import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.SlotItemHandler;
import net.xalcon.ecotec.common.tileentities.agriculture.TileEntityMachineBreeder;

public class ContainerMachineBreeder extends ContainerBase<TileEntityMachineBreeder>
{
	public ContainerMachineBreeder(GuiElementContext<TileEntityMachineBreeder> context)
	{
		super(context);

		IItemHandler itemHandler = this.tileEntity.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, null);

		for (int y = 0; y < 3; y++)
		{
			for (int x = 0; x < 3; x++)
			{
				this.addSlotToContainer(new SlotItemHandler(itemHandler, x + y * 3, 61 + x * 18, 16 + y * 18));
			}
		}
	}
}
