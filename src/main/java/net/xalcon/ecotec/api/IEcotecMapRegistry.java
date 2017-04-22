package net.xalcon.ecotec.api;

import com.google.common.collect.ImmutableList;

public interface IEcotecMapRegistry<T, V>
{
	void register(T entry);
	ImmutableList<T> getEntries();
	T find(V v);
}
