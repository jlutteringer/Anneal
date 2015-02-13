package org.alloy.metal.collections.list;

import org.alloy.metal.collections.ACollection;

public interface AList<T> extends ACollection<T> {
	public T get(long index);
}