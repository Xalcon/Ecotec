package net.xalcon.ecotec.common.tileentities;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.NonNullList;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemHandlerHelper;
import net.minecraftforge.items.ItemStackHandler;

import java.util.ArrayList;
import java.util.List;

public abstract class TileEntityMachine extends TileEntityInventory<ItemStackHandler>
{
	protected NonNullList<ItemStack> failedDrops;

	protected TileEntityMachine()
	{
		this.failedDrops = NonNullList.create();
	}

	public void dropItem(ItemStack itemStack)
	{
		for (EnumFacing facing : EnumFacing.VALUES)
		{
			TileEntity te = this.getWorld().getTileEntity(this.getPos().offset(facing));
			EnumFacing oppositeFacing = facing.getOpposite();
			if (te != null && te.hasCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, oppositeFacing))
			{
				IItemHandler itemHandler = te.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, oppositeFacing);
				itemStack = ItemHandlerHelper.insertItemStacked(itemHandler, itemStack, false);
				if (itemStack.isEmpty())
					break;
			}
		}

		if (!itemStack.isEmpty())
		{
			this.failedDrops.add(itemStack);
		}
	}

	public boolean dropItems(List<ItemStack> itemStacks)
	{
		for (ItemStack itemStack : itemStacks)
		{
			for (EnumFacing facing : EnumFacing.VALUES)
			{
				TileEntity te = this.getWorld().getTileEntity(this.getPos().offset(facing));
				EnumFacing oppositeFacing = facing.getOpposite();
				if (te != null && te.hasCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, oppositeFacing))
				{
					IItemHandler itemHandler = te.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, oppositeFacing);
					itemStack = ItemHandlerHelper.insertItemStacked(itemHandler, itemStack, false);
					if (itemStack.isEmpty())
						break;
				}
			}

			if (!itemStack.isEmpty())
			{
				this.failedDrops.add(itemStack);
			}
		}

		return this.failedDrops.size() == 0;
	}

	public boolean dropFailedItems()
	{
		List<ItemStack> dropList = new ArrayList<>();
		dropList.addAll(this.failedDrops);
		this.failedDrops.clear();
		return this.dropItems(dropList);
	}

	public boolean isInventoryClogged() { return this.failedDrops.size() > 0; }
}
