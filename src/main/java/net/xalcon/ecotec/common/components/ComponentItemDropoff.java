package net.xalcon.ecotec.common.components;

import net.minecraft.client.resources.I18n;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemHandlerHelper;
import net.xalcon.ecotec.api.components.IItemDropoff;
import net.xalcon.ecotec.common.tileentities.NbtSyncType;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class ComponentItemDropoff implements IItemDropoff
{
	private List<ItemStack> failedDrops = new ArrayList<>();
	private TileEntity tileEntity;

	public ComponentItemDropoff(TileEntity tileEntity)
	{
		this.tileEntity = tileEntity;
	}

	@Override
	public void setTileEntity(TileEntity tileEntity)
	{
		this.tileEntity = tileEntity;
	}

	@Override
	public boolean isClogged() { return this.failedDrops.size() > 0; }

	@Override
	public boolean tryDropCloggedItems(@Nullable EnumFacing worldDropDirection)
	{
		List<ItemStack> dropList = this.failedDrops;
		this.failedDrops = new ArrayList<>();
		return this.dropItems(dropList, worldDropDirection);
	}

	@Override
	public boolean dropItems(Collection<ItemStack> droppableItems, @Nullable EnumFacing worldDropDirection)
	{
		World world = this.tileEntity.getWorld();
		BlockPos pos = this.tileEntity.getPos();
		for(ItemStack itemStack : droppableItems)
		{
			if(itemStack.isEmpty()) continue;

			for (EnumFacing facing : EnumFacing.VALUES)
			{
				TileEntity te = world.getTileEntity(pos.offset(facing));
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
				this.failedDrops.add(itemStack);
		}
		return !this.isClogged();
	}

	@Override
	public void readSyncNbt(@Nonnull NBTTagCompound nbt, @Nonnull NbtSyncType type) { }

	@Override
	public void writeSyncNbt(@Nonnull NBTTagCompound nbt, @Nonnull NbtSyncType type) { }
}
