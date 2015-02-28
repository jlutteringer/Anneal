package org.alloy.metal.collections.map;

public interface MutableMapOperations<T, N> {
	public boolean put(T key, N value);

	public boolean remove(T key);

	public default void putAll(AMap<? extends T, ? extends N> otherMap) {
		otherMap.forEach((entry) -> put(entry.getKey(), entry.getValue()));
	}
}