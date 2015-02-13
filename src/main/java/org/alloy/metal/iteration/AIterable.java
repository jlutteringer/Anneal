package org.alloy.metal.iteration;

import org.alloy.metal.function.Flow;
import org.alloy.metal.function.Transduceable;
import org.alloy.metal.function.Transducer;
import org.alloy.metal.function.TransductionContext;

public interface AIterable<T> extends Transduceable<T> {
	@Override
	public default <R> Flow<R> compose(Transducer<R, T> transducer) {
		return new TransductionContext<R, T>(this.iterator(), transducer);
	}
}