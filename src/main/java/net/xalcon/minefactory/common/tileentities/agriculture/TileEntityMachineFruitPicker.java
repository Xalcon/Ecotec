package net.xalcon.minefactory.common.tileentities.agriculture;

import net.minecraft.block.BlockCarrot;
import net.minecraft.block.BlockCrops;
import net.minecraft.block.BlockPotato;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.xalcon.minefactory.common.blocks.BlockMachineBase;
import net.xalcon.minefactory.common.tileentities.TileEntityMachineWorldInteractive;

import java.util.List;

public class TileEntityMachineFruitPicker extends TileEntityMachineWorldInteractive
{
	public TileEntityMachineFruitPicker()
	{
		super(0);
	}

	@Override
	public String getUnlocalizedName()
	{
		return "machine_fruit_picker";
	}

	@Override
	public int getMaxIdleTicks()
	{
		return 100;
	}

	@Override
	public int getMaxProgressTicks()
	{
		return 1;
	}

	@Override
	protected boolean doWork()
	{
		EnumFacing facing = this.getWorld().getBlockState(this.getPos()).getValue(BlockMachineBase.FACING);
		BlockPos cropPos = this.getPos().offset(facing);
		IBlockState state = this.getWorld().getBlockState(cropPos);
		return harvestCrop(cropPos, state);
	}

	private boolean harvestCrop(BlockPos cropPos, IBlockState state)
	{
		if (state.getBlock() instanceof BlockCrops)
		{
			BlockCrops crop = (BlockCrops) state.getBlock();
			if (crop.isMaxAge(state))
			{
				List<ItemStack> drops = crop.getDrops(this.getWorld(), cropPos, state, 0);

				Item seedItem = crop.getItemDropped(crop.withAge(0), this.world.rand, 0);
				for (ItemStack drop : drops)
				{
					if (!(drop.getItem() == seedItem) || crop instanceof BlockCarrot || crop instanceof BlockPotato)
					{
						drops.remove(drop);
						break;
					}
				}

				this.dropItems(drops);
				return world.setBlockState(cropPos, crop.withAge(0));
			}
		}
		return false;
	}
}
