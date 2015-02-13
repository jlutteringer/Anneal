package org.alloy.metal.collections.list;

import java.util.List;

import com.google.common.collect.Lists;

public class _Lists {
	public static <T> MutableList<T> empty() {
		return wrap(Lists.newArrayList());
	}

	@SafeVarargs
	public static <T> MutableList<T> list(T... elements) {
		return wrap(Lists.newArrayList(elements));
	}

	public static <T> MutableList<T> wrap(List<T> list) {
		return new MutableListAdapter<T>(list);
	}
}