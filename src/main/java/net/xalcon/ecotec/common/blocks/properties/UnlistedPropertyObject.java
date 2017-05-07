package net.xalcon.ecotec.common.blocks.properties;

import net.minecraftforge.common.property.IUnlistedProperty;

import java.util.function.Function;
import java.util.function.Predicate;

public class UnlistedPropertyObject<T> implements IUnlistedProperty<T>
{
	private Class<T> clazz;
	private String name;
	private Predicate<T> validator;
	private Function<T, String> toStringFunc;

	public UnlistedPropertyObject(String name, Class<T> clazz)
	{
		this(name, clazz, t -> true, Object::toString);
	}

	public UnlistedPropertyObject(String name, Class<T> clazz, Predicate<T> validator, Function<T, String> toStringFunc)
	{
		this.name = name;
		this.clazz = clazz;
		this.validator = validator;
		this.toStringFunc = toStringFunc;
	}

	@Override
	public String getName()
	{
		return this.name;
	}

	@Override
	public boolean isValid(T value)
	{
		return this.validator.test(value);
	}

	@Override
	public Class<T> getType()
	{
		return this.clazz;
	}

	@Override
	public String valueToString(T value)
	{
		return this.toStringFunc.apply(value);
	}
}
