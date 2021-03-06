package net.xalcon.ecotec;

import com.google.common.collect.ImmutableList;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.xalcon.ecotec.api.*;

import java.util.HashMap;
import java.util.HashSet;
import java.util.function.Function;

public class EcotecRegistries
{
	public final static IEcotecMapRegistry<IEcotecPlantable, Item> Plantables = new MapRegistryImpl<>(IEcotecPlantable::getSeedItem);
	public final static IEcotecMapRegistry<IEcotecHarvestable, Block> Harvestables = new MapRegistryImpl<>(IEcotecHarvestable::getPlantedBlock);
	public final static IEcotecListRegistry<IEntityRancherLogic> Ranchables = new ListRegistryImpl<>();

	private static class MapRegistryImpl<T, V> implements IEcotecMapRegistry<T, V>
	{
		private HashMap<V, T> registry = new HashMap<>();
		private Function<T, V> mapFunc;

		MapRegistryImpl(Function<T, V> mapFunc)
		{
			this.mapFunc = mapFunc;
		}

		@Override
		public void register(T entry)
		{
			this.registry.put(this.mapFunc.apply(entry), entry);
		}

		@Override
		public ImmutableList<T> getEntries()
		{
			return ImmutableList.copyOf(this.registry.values());
		}

		@Override
		public T find(V v)
		{
			return this.registry.getOrDefault(v, null);
		}
	}

	private static class ListRegistryImpl<T> implements IEcotecListRegistry<T>
	{
		private HashSet<T> registry = new HashSet<>();

		@Override
		public void register(T entry)
		{
			this.registry.add(entry);
		}

		@Override
		public ImmutableList<T> getEntries()
		{
			return ImmutableList.copyOf(this.registry);
		}
	}
}
