package net.xalcon.minefactory.common.inventory;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.xalcon.minefactory.common.GuiType;
import net.xalcon.minefactory.common.tileentities.machines.TileEntityMachineBreeder;

public class ContainerMachineBreeder extends ContainerBase<TileEntityMachineBreeder>
{
	public ContainerMachineBreeder(GuiType.ContextInfo context)
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
