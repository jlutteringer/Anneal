package org.alloy.metal.iteration;

import com.google.common.collect.Iterables;

public abstract class IterableWithToString<T> implements AIterable<T> {
	@Override
	public String toString() {
		return Iterables.toString(this);
	}
}