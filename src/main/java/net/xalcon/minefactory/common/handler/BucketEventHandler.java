package net.xalcon.minefactory.common.handler;

import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Items;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraftforge.common.ForgeModContainer;
import net.minecraftforge.event.entity.player.FillBucketEvent;
import net.minecraftforge.fluids.BlockFluidClassic;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.UniversalBucket;
import net.minecraftforge.fml.common.eventhandler.Event;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.xalcon.minefactory.common.blocks.fluids.BlockMFFluid;

public class BucketEventHandler
{
	@SubscribeEvent
	public void onRightClickHoldingBucket(FillBucketEvent event)
	{
		if (event.getWorld().isRemote) return;
		// check we're using a bucket, on a block we can modify
		if (event.getEmptyBucket().getItem() != Items.BUCKET)
		{
			return;
		}
		if (event.getTarget() == null || event.getTarget().typeOfHit != RayTraceResult.Type.BLOCK)
		{
			return;
		}
		BlockPos blockpos = event.getTarget().getBlockPos();
		if (!event.getWorld().isBlockModifiable(event.getEntityPlayer(), blockpos))
		{
			return;
		}
		if (!event.getEntityPlayer().canPlayerEdit(blockpos.offset(event.getTarget().sideHit), event.getTarget().sideHit, event.getEmptyBucket()))
		{
			return;
		}

		// determine if the block is one of our fluids
		IBlockState iblockstate = event.getWorld().getBlockState(blockpos);
		Fluid filled_fluid;
		if (iblockstate.getBlock() instanceof BlockMFFluid && iblockstate.getValue(BlockFluidClassic.LEVEL) == 0)
		{
			filled_fluid = ((BlockMFFluid) iblockstate.getBlock()).getFluid();
		} else
		{
			return;
		}

		// remove the fluid and return the appropriate filled bucket
		event.setResult(Event.Result.ALLOW);
		event.setFilledBucket(UniversalBucket.getFilledBucket(ForgeModContainer.getInstance().universalBucket, filled_fluid));
		event.getWorld().setBlockToAir(blockpos);
	}
}
