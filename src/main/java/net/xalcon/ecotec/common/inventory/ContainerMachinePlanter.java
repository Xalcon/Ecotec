package net.xalcon.ecotec.common.inventory;

import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.SlotItemHandler;
import net.xalcon.ecotec.common.GuiType;
import net.xalcon.ecotec.common.tileentities.agriculture.TileEntityMachinePlanter;

public class ContainerMachinePlanter extends ContainerBase<TileEntityMachinePlanter>
{
	public ContainerMachinePlanter(GuiType.ContextInfo context)
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