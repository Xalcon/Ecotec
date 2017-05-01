package net.xalcon.ecotec.api.components;

import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.xalcon.ecotec.core.IEcotecComponent;

import javax.annotation.Nullable;
import java.util.Collection;

/**
 * This component will allow others to "drop" items into other inventories as if it was done by the block
 * It also stores failed items
 * (I dont like the name :/)
 */
public interface IItemDropoff extends IEcotecComponent
{
	/**
	 * sets the tile entity this IItemDropoff is working off
	 * (Use this if you request the default implementation from the capability)
	 * @param tileEntity the tileentity
	 */
	void setTileEntity(TileEntity tileEntity);

	/**
	 * Returns if the last dropItems() call wasnt able to drop off all its items
	 * @return true if there are still items which need to be dropped of, otherwise false
	 */
	boolean isClogged();

	/**
	 * Tries to drop off stored items
	 * @param worldDropDirection the direction to drop off items in the world.
	 *                           if null, no items will be dropped into the world
	 * @return true if all items were successfully dropped of, otherwise false
	 */
	boolean tryDropCloggedItems(@Nullable EnumFacing worldDropDirection);

	/**
	 * Tries to drop off the given items into adjacent inventories or the world
	 * @param droppableItems list of items that should be dropped off
	 * @param worldDropDirection the direction to drop off items in the world.
	 *                           if null, no items will be dropped into the world
	 * @return true if all items were successfully dropped of, otherwise false
	 */
	boolean dropItems(Collection<ItemStack> droppableItems, @Nullable EnumFacing worldDropDirection);
}
