package net.xalcon.ecotec.api.components;

public interface IStateUpdatable extends IEcotecComponent<IStateUpdatable>
{
	/**
	 * schedule an update (sync of nbt, etc)
	 */
	void markDirty();
}
