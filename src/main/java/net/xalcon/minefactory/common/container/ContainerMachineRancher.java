package net.xalcon.minefactory.common.container;

import net.minecraft.inventory.Slot;
import net.xalcon.minefactory.common.GuiType;
import net.xalcon.minefactory.common.tileentities.machines.TileEntityMachineRancher;

public class ContainerMachineRancher extends ContainerBase<TileEntityMachineRancher>
{
	public ContainerMachineRancher(GuiType.ContextInfo context)
	{
		super(context);

		for(int y = 0; y < 3; y++)
		{
			for (int x = 0; x < 3; x++)
			{
				this.addSlotToContainer(new Slot(this.tileEntity, 1 + x + y * 3, 61 + x * 18, 16 + y * 18));
			}
		}
	}
}
