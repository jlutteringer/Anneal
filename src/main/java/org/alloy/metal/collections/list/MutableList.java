package org.alloy.metal.collections.list;

import org.alloy.metal.collections.MutableCollection;

public interface MutableList<T> extends MutableCollection<T>, AList<T> {
	public boolean add(long index, T element);

	public T set(long index, T element);
}