package net.xalcon.ecotec.common.components;

import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.InventoryHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;
import net.minecraftforge.fluids.capability.IFluidHandler;
import net.minecraftforge.fluids.capability.IFluidHandlerItem;
import net.minecraftforge.fluids.capability.IFluidTankProperties;
import net.xalcon.ecotec.api.components.IFluidItemInteraction;
import net.xalcon.ecotec.common.init.ModCaps;

public class ComponentFluidItemInteraction implements IFluidItemInteraction
{
	private IFluidHandler fluidHandler;
	private boolean allowBucketInsert;
	private boolean allowBucketExtract;

	public ComponentFluidItemInteraction(boolean allowBucketInsert, boolean allowBucketExtract)
	{
		this.allowBucketInsert = allowBucketInsert;
		this.allowBucketExtract = allowBucketExtract;
	}

	@Override
	public void initialize(ICapabilityProvider provider)
	{
		this.fluidHandler = provider.getCapability(CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY, null);
	}

	@Override
	public void invalidate()
	{
		this.fluidHandler = null;
	}

	@Override
	public boolean interact(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ)
	{
		// support for stackable itemstacks: copy it, reduce size to 1 and if we can fill it, shrink() the original
		ItemStack heldItem = playerIn.getHeldItem(hand).copy();
		heldItem.setCount(1);
		IFluidHandlerItem fluidHandlerItem = heldItem.getCapability(CapabilityFluidHandler.FLUID_HANDLER_ITEM_CAPABILITY, null);
		if(fluidHandlerItem == null) return false;

		// lets check if we can put fluid from the fluidHandler into the item
		if(this.allowBucketExtract)
		{
			for(IFluidTankProperties fluidTankProp : this.fluidHandler.getTankProperties())
			{
				FluidStack fluidStack = fluidTankProp.getContents();
				if(fluidStack == null || fluidStack.amount == 0) continue;
				for(IFluidTankProperties fluidItemProp : fluidHandlerItem.getTankProperties())
				{
					if(fluidItemProp.canFillFluidType(fluidStack))
					{
						int amount = fluidHandlerItem.fill(fluidStack, true);
						if(amount > 0)
						{
							playerIn.getHeldItem(hand).shrink(1);
							if(!playerIn.inventory.addItemStackToInventory(fluidHandlerItem.getContainer()))
							{
								InventoryHelper.spawnItemStack(worldIn, pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5, fluidHandlerItem.getContainer());
							}
							FluidStack drainStack = fluidStack.copy();
							drainStack.amount = amount;
							this.fluidHandler.drain(drainStack, true);

							return true;
						}
					}
				}
			}
		}
		if(this.allowBucketInsert)
		{
			for(IFluidTankProperties fluidTankProp : this.fluidHandler.getTankProperties())
			{
				if(!fluidTankProp.canFill()) continue;
				FluidStack fluidStack = fluidTankProp.getContents();
				int maxDrain = fluidTankProp.getCapacity();
				if(fluidStack != null)
					maxDrain -= fluidStack.amount;
				if(maxDrain <= 0) continue;

				for(IFluidTankProperties fluidItemProp : fluidHandlerItem.getTankProperties())
				{
					if(!fluidItemProp.canDrain()) continue;

					FluidStack drainStack = fluidHandlerItem.drain(maxDrain, false);
					if(drainStack == null) continue;
					int amount = this.fluidHandler.fill(drainStack, true);
					fluidHandlerItem.drain(new FluidStack(drainStack, amount), true);

					playerIn.getHeldItem(hand).shrink(1);
					if(!playerIn.inventory.addItemStackToInventory(fluidHandlerItem.getContainer()))
					{
						InventoryHelper.spawnItemStack(worldIn, pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5, fluidHandlerItem.getContainer());
					}
					return true;
				}
			}
		}

		return false;
	}

	@Override
	public Capability<IFluidItemInteraction> getCapability()
	{
		return ModCaps.getBucketInteractionCap();
	}
}
