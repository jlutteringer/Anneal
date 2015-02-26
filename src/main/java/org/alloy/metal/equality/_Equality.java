package org.alloy.metal.equality;

import java.util.Comparator;

public class _Equality {
	public static <T, N> Equalitor<T, N> notEqual() {
		return (first, second) -> false;
	}

	public static <T, N> Equalitor<T, N> natural() {
		return (first, second) -> first.equals(second);
	}

	public static <T> SymmetricEqualitor<T> comparator(Comparator<T> comparator) {
		return (first, second) -> comparator.compare(first, second) == 0;
	}
}
