package org.alloy.metal.collections;

import org.alloy.metal.base.Mutable;

public interface MutableCollection<T> extends ACollection<T>, Mutable {
	public boolean add(T element);

	public boolean remove(T element);

	public default boolean addAll(Iterable<T> iterable) {
		boolean added = true;
		for (T element : iterable) {
			if (!add(element)) {
				added = false;
			}
		}
		return added;
	}
}