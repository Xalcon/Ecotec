package net.xalcon.ecotec.common.tileentities.agriculture;

import net.minecraft.block.state.IBlockState;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.items.ItemStackHandler;
import net.xalcon.ecotec.api.IEcotecPlantable;
import net.xalcon.ecotec.EcotecRegistries;
import net.xalcon.ecotec.common.init.ModBlocks;
import net.xalcon.ecotec.common.tileentities.TileEntityMachineWorldInteractive;
import net.xalcon.ecotec.common.util.IterativeAreaWalker;

public class TileEntityMachinePlanter extends TileEntityMachineWorldInteractive
{
	@Override
	public String getUnlocalizedName()
	{
		return ModBlocks.MachinePlanter.getUnlocalizedName();
	}

	@Override
	public int getMaxIdleTicks()
	{
		return 10;
	}

	@Override
	public int getMaxProgressTicks()
	{
		return 1;
	}

	private IterativeAreaWalker areaWalker;

	@Override
	protected boolean doWork()
	{
		if(this.areaWalker == null)
		{
			int radius = this.getWorkRadius();
			AxisAlignedBB area = new AxisAlignedBB(this.getPos().offset(EnumFacing.UP, 2)).expand(radius, 0, radius);
			this.areaWalker = new IterativeAreaWalker(area);
		}

		BlockPos plantPos = this.areaWalker.getNext();
		for(int i = 0; i < this.inventory.getSlots(); i++)
		{
			ItemStack plantableStack = this.inventory.getStackInSlot(i);
			if(plantableStack.isEmpty()) continue;

			IEcotecPlantable plantable = EcotecRegistries.Plantables.find(plantableStack.getItem());
			if(plantable == null || !plantable.canBePlantedAt(this.getWorld(), plantPos, plantableStack)) continue;

			IBlockState plantBlockState = plantable.getPlantedBlock(this.getWorld(), plantPos, plantableStack);
			plantable.onPlanting(this.getWorld(), plantPos, plantableStack, plantBlockState);
			this.world.setBlockState(plantPos, plantBlockState);
			plantable.onPlanted(this.getWorld(), plantPos, plantableStack, plantBlockState);
			plantableStack.shrink(1);
			return true;
		}

		return false;
	}

	@Override
	protected ItemStackHandler createInventory()
	{
		return new ItemStackHandler(9);
	}
}
