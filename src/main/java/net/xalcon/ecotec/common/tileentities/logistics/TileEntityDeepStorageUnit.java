package net.xalcon.ecotec.common.tileentities.logistics;

import net.minecraft.item.ItemStack;
import net.xalcon.ecotec.common.inventory.DSUItemStackHandler;
import net.xalcon.ecotec.common.tileentities.TileEntityInventory;

public class TileEntityDeepStorageUnit extends TileEntityInventory<DSUItemStackHandler>
{
	public TileEntityDeepStorageUnit()
	{
		super(new DSUItemStackHandler());
	}

	@Override
	public String getUnlocalizedName()
	{
		return "deep_storage_unit";
	}

	@Override
	public ItemStack insertItemStack(ItemStack itemStack)
	{
		return this.inventory.insertItem(0, itemStack, false);
	}
}
