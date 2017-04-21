package net.xalcon.ecotec.common;

import com.google.common.collect.ImmutableList;
import net.minecraft.item.Item;
import net.xalcon.ecotec.api.IEcotecPlantable;
import net.xalcon.ecotec.api.IEcotecRegistry;

import java.util.HashMap;
import java.util.function.Function;

public class EcotecRegistries
{
	public final static IEcotecRegistry<IEcotecPlantable, Item> Plantables = new RegistryImpl<>(IEcotecPlantable::getSeedItem);

	private static class RegistryImpl<T, V> implements IEcotecRegistry<T, V>
	{
		private HashMap<V, T> registry = new HashMap<>();
		private Function<T, V> mapFunc;

		RegistryImpl(Function<T, V> mapFunc)
		{
			this.mapFunc = mapFunc;
		}

		@Override
		public void register(T entry)
		{
			registry.put(this.mapFunc.apply(entry), entry);
		}

		@Override
		public ImmutableList<T> getEntries()
		{
			return ImmutableList.copyOf(this.registry.values());
		}

		@Override
		public T find(V v)
		{
			return registry.getOrDefault(v, null);
		}
	}
}
