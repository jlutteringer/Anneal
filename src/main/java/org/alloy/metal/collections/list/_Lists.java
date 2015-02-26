package org.alloy.metal.collections.list;

import java.util.List;

import com.google.common.collect.Lists;

public class _Lists {
	public static <T> MutableList<T> empty() {
		return wrap(Lists.newArrayList());
	}

	@SafeVarargs
	public static <T> MutableList<T> list(T... items) {
		return wrap(Lists.newArrayList(items));
	}

	public static <T> MutableList<T> list(Iterable<T> items) {
		return wrap(Lists.newArrayList(items));
	}

	@SafeVarargs
	public static <T> MutableList<T> list(Iterable<T> items, T... otherItems) {
		MutableList<T> list = list(items);
		list.addAll(list(otherItems));
		return list;
	}

	public static <T> List<T> list(List<T> list) {
		if (list == null) {
			return Lists.newArrayList();
		}
		return list;
	}

	@SafeVarargs
	public static <T> List<T> utilList(T... items) {
		return Lists.newArrayList(items);
	}

	public static <T> List<T> utilList(Iterable<T> items) {
		return Lists.newArrayList(items);
	}

	public static <T> List<T> utilList(List<T> list) {
		if (list == null) {
			return Lists.newArrayList();
		}
		return list;
	}

	public static <T> MutableList<T> wrap(List<T> list) {
		return new MutableListAdapter<T>(list);
	}
}