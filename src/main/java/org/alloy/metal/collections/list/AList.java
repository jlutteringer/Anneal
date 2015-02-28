package org.alloy.metal.collections.list;

import java.util.List;

import org.alloy.metal.collections.ACollection;

public interface AList<T> extends ACollection<T> {
	public T get(int index);

	public List<T> asList();
}