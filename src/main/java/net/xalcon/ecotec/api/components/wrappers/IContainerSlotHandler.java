package net.xalcon.ecotec.api.components.wrappers;

import net.minecraft.inventory.Slot;

public interface IContainerSlotHandler
{
	/**
	 * adds a slot to the guiprovider
	 * @param slot the slot
	 */
	void addSlot(Slot slot);
}
