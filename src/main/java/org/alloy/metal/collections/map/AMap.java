package org.alloy.metal.collections.map;

import java.util.Map;

public interface AMap<T, N> extends Mapper<T, N>, MapOperations<T, N> {
	public Map<T, N> asMap();
}