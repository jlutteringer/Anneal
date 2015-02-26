package org.alloy.metal.equality;

import java.util.function.BiFunction;

@FunctionalInterface
public interface Equalitor<T, N> extends BiFunction<T, N, Boolean> {
	public default Equalitor<N, T> reverse() {
		return (first, second) -> this.apply(second, first);
	}
}