package net.xalcon.ecotec.common.farmables.plantables;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.xalcon.ecotec.api.IEcotecPlantable;

import javax.annotation.Nonnull;

public class PlantableBasic implements IEcotecPlantable
{
	private Block plantBlock;
	private Item plantItem;

	public PlantableBasic(Block plantBlock)
	{
		this(plantBlock, Item.getItemFromBlock(plantBlock));
	}

	public PlantableBasic(Block plantBlock, Item plantItem)
	{
		this.plantBlock = plantBlock;
		this.plantItem = plantItem;
	}

	@Nonnull
	@Override
	public Item getSeedItem()
	{
		return this.plantItem;
	}

	@Override
	public boolean canBePlantedAt(@Nonnull World world, @Nonnull BlockPos pos, @Nonnull ItemStack itemStack)
	{
		return !world.isAirBlock(pos.down()) && this.plantBlock.canPlaceBlockAt(world, pos);
	}

	@Nonnull
	@Override
	public IBlockState getPlantedBlock(@Nonnull World world, @Nonnull BlockPos pos, @Nonnull ItemStack itemStack)
	{
		// TODO: replace with getStateForPlacement()
		//noinspection deprecation
		return this.plantBlock.getStateFromMeta(itemStack.getMetadata());
	}

	@Override
	public void onPlanting(World world, BlockPos pos, ItemStack itemStack, IBlockState blockState) { /* 0x90 */ }
}
