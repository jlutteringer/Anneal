package org.alloy.metal.collections.map;

import org.alloy.metal.flow.Source;

public interface MutableMultimap<T, N> extends AMultimap<T, N>, MutableMapOperations<T, N> {
	// FUTURE fix the return value
	public default boolean putAll(T key, Source<? extends N> values) {
		values.forEach((value) -> {
			put(key, value);
		});

		return true;
	}
}