package org.alloy.metal.collections;

import java.util.Collection;

public class _Collections {
	public static <T> ACollection<T> wrap(Collection<T> collection) {
		return new ACollectionAdapter<>(collection);
	}
}