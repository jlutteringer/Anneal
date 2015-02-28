package org.alloy.metal.collections.map;

public interface Mapper<T, N> {
	public boolean containsKey(T key);

	public N get(T key);
}