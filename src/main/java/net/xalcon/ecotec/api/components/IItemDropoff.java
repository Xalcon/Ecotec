package net.xalcon.ecotec.api.components;

import net.minecraft.item.ItemStack;

import java.util.Collection;

/**
 * This component will allow others to "drop" items into other inventories as if it was done by the block
 * It also stores failed items
 * (I dont like the name :/)
 */
public interface IItemDropoff extends IEcotecComponent<IItemDropoff>
{
	/**
	 * Returns if the last dropItems() call wasnt able to drop off all its items
	 * @return true if there are still items which need to be dropped of, otherwise false
	 */
	boolean isClogged();

	/**
	 * Tries to drop off stored items
	 * @return true if all items were successfully dropped of, otherwise false
	 */
	boolean tryDropCloggedItems();

	/**
	 * Tries to drop off the given items into adjacent inventories
	 * @param droppableItems list of items that should be dropped off
	 * @return true if all items were successfully dropped of, otherwise false
	 */
	boolean dropItems(Collection<ItemStack> droppableItems);
}
