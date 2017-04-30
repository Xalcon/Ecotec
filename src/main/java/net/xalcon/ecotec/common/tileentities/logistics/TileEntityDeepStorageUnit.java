package net.xalcon.ecotec.common.tileentities.logistics;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.items.CapabilityItemHandler;
import net.xalcon.ecotec.common.init.ModBlocks;
import net.xalcon.ecotec.common.inventories.itemstackhandler.ItemStackHandlerDSU;
import net.xalcon.ecotec.common.tileentities.NbtSyncType;
import net.xalcon.ecotec.common.tileentities.TileEntityBaseNew;
import net.xalcon.ecotec.common.tileentities.TileEntityInventory;

public class TileEntityDeepStorageUnit extends TileEntityBaseNew
{
	public TileEntityDeepStorageUnit()
	{
		this.addCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, new ItemStackHandlerDSU());
	}
}
