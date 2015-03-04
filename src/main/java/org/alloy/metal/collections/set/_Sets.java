package org.alloy.metal.collections.set;

import java.util.Set;

import com.google.common.collect.Sets;

public class _Sets {
	public static <T> MutableSet<T> wrap(Set<T> set) {
		return new MutableSetAdapter<>(set);
	}

	@SafeVarargs
	public static <T> MutableSet<T> set(T... elements) {
		return wrap(Sets.newHashSet(elements));
	}
}
