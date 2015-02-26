package org.alloy.metal.flow;

import org.alloy.metal.transducer.Transducable;

public interface Source<T> extends Transducable<T>, Completable<T> {
	public Flow<T> flow();
}