package net.xalcon.ecotec.api;

import com.google.common.collect.ImmutableList;

public interface IEcotecRegistry<T, V>
{
	void register(T entry);
	ImmutableList<T> getEntries();
	T find(V v);
}
