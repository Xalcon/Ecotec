package net.xalcon.ecotec.api.components;

public interface IStateUpdatable extends IEcotecComponent
{
	/**
	 * schedule an update (sync of nbt, etc)
	 */
	void markDirty();
}
