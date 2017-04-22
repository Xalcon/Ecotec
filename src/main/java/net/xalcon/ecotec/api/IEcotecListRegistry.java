package net.xalcon.ecotec.api;

import com.google.common.collect.ImmutableList;

public interface IEcotecListRegistry<T>
{
	void register(T entry);
	ImmutableList<T> getEntries();
}
