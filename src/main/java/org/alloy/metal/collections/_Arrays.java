package org.alloy.metal.collections;

import com.google.common.collect.Iterables;

public class _Arrays {
	@SafeVarargs
	public static <T> T[] array(T... items) {
		return items;
	}

	public static <T> T[] array(Iterable<T> iterable, Class<T> clazz) {
		return Iterables.toArray(iterable, clazz);
	}
}
