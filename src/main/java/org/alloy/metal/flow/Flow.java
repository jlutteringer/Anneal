package org.alloy.metal.flow;

public interface Flow<T> extends Source<T> {
	public default Flow<T> parallelFlow() {
		throw new UnsupportedOperationException();
	}
}