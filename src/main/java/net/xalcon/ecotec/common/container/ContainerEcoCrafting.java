package net.xalcon.ecotec.common.container;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.InventoryCraftResult;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.xalcon.ecotec.common.tileentities.logistics.TileEntityProjectTable;

public class ContainerEcoCrafting extends ContainerBase
{
	private InventoryCraftResult craftResult;

	public ContainerEcoCrafting(EntityPlayer player, TileEntity tileEntity)
	{
		super(player, tileEntity);
		this.craftResult = ((TileEntityProjectTable)tileEntity).getCraftOutputHandler();
	}

	/**
	 * Called to determine if the current slot is valid for the stack merging (double-click) code. The stack passed in
	 * is null for the initial slot that was double-clicked.
	 */
	public boolean canMergeSlot(ItemStack stack, Slot slotIn)
	{
		return slotIn.inventory != this.craftResult && super.canMergeSlot(stack, slotIn);
	}
}
