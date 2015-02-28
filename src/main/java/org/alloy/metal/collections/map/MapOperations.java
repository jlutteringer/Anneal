package org.alloy.metal.collections.map;

import java.util.Map.Entry;

import org.alloy.metal.collections.ACollection;
import org.alloy.metal.collections.set.ASet;

public interface MapOperations<T, N> extends ASet<Entry<T, N>> {
	public boolean containsValue(N value);

	public ACollection<N> values();
}