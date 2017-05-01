package net.xalcon.ecotec.common.components;

import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemHandlerHelper;
import net.xalcon.ecotec.api.components.IBlockLocation;
import net.xalcon.ecotec.api.components.IItemDropoff;
import net.xalcon.ecotec.common.init.ModCaps;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class ComponentItemDropoff implements IItemDropoff
{
	private List<ItemStack> failedDrops = new ArrayList<>();
	private IBlockLocation loc;

	@Override
	public void initialize(ICapabilityProvider provider)
	{
		this.loc = provider.getCapability(ModCaps.getBlockLocationCap(), null);
	}

	@Override
	public void invalidate()
	{
		this.loc = null;
	}

	@Override
	public Capability<IItemDropoff> getCapability()
	{
		return ModCaps.getItemDropoffCap();
	}

	@Override
	public boolean isClogged() { return this.failedDrops.size() > 0; }

	@Override
	public boolean tryDropCloggedItems()
	{
		List<ItemStack> dropList = this.failedDrops;
		this.failedDrops = new ArrayList<>();
		return this.dropItems(dropList);
	}

	@Override
	public boolean dropItems(Collection<ItemStack> droppableItems)
	{
		World world = this.loc.getWorld();
		BlockPos pos = this.loc.getPos();
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
}
