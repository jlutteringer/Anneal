package org.alloy.metal.collections;

import java.util.Iterator;

import org.alloy.metal.base.Mutable;
import org.alloy.metal.flow.Source;

public interface MutableCollection<T> extends ACollection<T>, Mutable {
	public boolean add(T element);

	public boolean remove(T element);

	public default boolean addAll(Source<? extends T> source) {
		boolean added = true;

		Iterator<? extends T> iterator = source.cursor().toIterator();
		while (iterator.hasNext()) {
			if (!add(iterator.next())) {
				added = false;
			}
		}

		return added;
	}
}