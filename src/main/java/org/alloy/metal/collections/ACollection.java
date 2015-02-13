package org.alloy.metal.collections;

import org.alloy.metal.iteration.AIterable;

public interface ACollection<T> extends AIterable<T> {
	public SizeInfo getSizeInfo();

	public boolean contains(T element);

	public default boolean containsAll(Iterable<T> iterable) {
		for (T element : iterable) {
			if (!contains(element)) {
				return false;
			}
		}

		return true;
	}

	public default boolean isEmpty() {
		if (this.iterator().hasNext()) {
			return false;
		}
		return true;
	}
}