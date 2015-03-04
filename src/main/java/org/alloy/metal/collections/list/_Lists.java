package org.alloy.metal.collections.list;

import java.util.Collections;
import java.util.LinkedList;
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

	@SafeVarargs
	public static <T> AList<T> immutableList(T... items) {
		return wrap(Collections.unmodifiableList(Lists.newArrayList(items)));
	}

	public static <T> AList<T> immutableList(Iterable<T> items) {
		return wrap(Collections.unmodifiableList(Lists.newArrayList(items)));
	}

	@SafeVarargs
	public static <T> AList<T> immutableList(Iterable<T> items, T... otherItems) {
		MutableList<T> list = list(items);
		list.addAll(list(otherItems));
		return immutableList(list);
	}

	@SafeVarargs
	public static <T> MutableLinkedList<T> linkedList(T... items) {
		LinkedList<T> list = Lists.newLinkedList();
		for (T item : items) {
			list.add(item);
		}
		return wrap(list);
	}

	@SafeVarargs
	public static <T> List<T> utilList(T... items) {
		return Lists.newArrayList(items);
	}

	public static <T> List<T> utilList(Iterable<T> items) {
		return Lists.newArrayList(items);
	}

	@SafeVarargs
	public static <T> List<T> utilList(Iterable<T> items, T... otherItems) {
		List<T> list = utilList(items);
		list.addAll(utilList(otherItems));
		return list;
	}

	public static <T> List<T> utilList(List<T> list) {
		if (list == null) {
			return Lists.newArrayList();
		}
		return list;
	}

	public static <T> MutableList<T> wrap(List<T> list) {
		if (list == null) {
			return list();
		}
		return new MutableListAdapter<T>(list);
	}

	public static <T> MutableLinkedList<T> wrap(LinkedList<T> list) {
		if (list == null) {
			return linkedList();
		}
		return new MutableLinkedListAdapter<T>(list);
	}
}