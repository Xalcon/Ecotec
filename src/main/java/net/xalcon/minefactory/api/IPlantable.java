package net.xalcon.minefactory.api;

import net.minecraft.block.state.IBlockState;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import javax.annotation.Nonnull;

public interface IPlantable
{
	/**
	 * Must return the seed item this IPlantable is managing
	 * @return the seed item this IPlantabe is managing
	 */
	@Nonnull
	Item getSeedItem();

	/**
	 * Is used to determine if the given ItemStack is a valid plantable item.
	 * This will never be called with a different item than returned by {@link #getSeedItem()}
	 * You only need to implement this, if you need to handle special NBT or metadata to determine plantability.
	 * @param stack the itemstack that the harvester intends to plant
	 * @return True if the ItemStack is a valid plantable item, otherwise false
	 */
	default boolean isPlantable(@Nonnull ItemStack stack) { return true; }

	/**
	 * This is used to determine if the given item stack can be planted at the given location.
	 * This function will only be called if {@link #isPlantable(ItemStack)} returned true.
	 * @param world the world this IPlantable is going to get planted
	 * @param pos the position in the world
	 * @param itemStack the itemstack that is going to get planted
	 * @return True if the itemStack can be planted at the given position, otherwise false.
	 */
	boolean canBePlantedAt(@Nonnull World world, @Nonnull BlockPos pos, @Nonnull ItemStack itemStack);

	/**
	 * This blockstate is used at the given position.
	 * Will only be called if {@link #canBePlantedAt(World, BlockPos, ItemStack)} returned true
	 * @param world the world
	 * @param pos the position
	 * @param itemStack the item stack
	 * @return this blockstate will be placed at the given position
	 */
	@Nonnull
	IBlockState getPlantedBlock(@Nonnull World world, @Nonnull BlockPos pos, @Nonnull ItemStack itemStack);

	/**
	 * This will be called before the plant will be placed.
	 * Should be used for any pre-planting tasks like tilling the earth, etc
	 * @param world the world
	 * @param pos the position the plant is going to be
	 * @param itemStack the item stack that will be planted
	 */
	void onPlanting(World world, BlockPos pos, ItemStack itemStack);

	/**
	 * This will be called after the plant has been placed. Usually empty.
	 * @param world the world
	 * @param pos the position the plant was placed at
	 * @param itemStack the item stack that was planted
	 */
	default void onPlanted(World world, BlockPos pos, ItemStack itemStack) { }
}
