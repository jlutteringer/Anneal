package org.alloy.metal.iteration;

import java.util.function.Consumer;

import org.alloy.metal.flow.Completable;
import org.alloy.metal.flow.Flow;
import org.alloy.metal.flow.FlowingSource;
import org.alloy.metal.flow.TransductionPipeline.BaseTransductionPipeline;
import org.alloy.metal.transducer.Transducer;

public interface AIterable<T> extends Iterable<T>, FlowingSource<T>, Completable<T> {
	@Override
	public default Flow<T> flow() {
		return new BaseTransductionPipeline<T>(this.iterator());
	}

	@Override
	default void forEach(Consumer<? super T> action) {
		this.flow().forEach(action);
	}

	@Override
	public default <R> Flow<R> compose(Transducer<R, T> transducer) {
		return this.flow().compose(transducer);
	}

	@Override
	public default Cursor<T> cursor() {
		return _Iteration.cursor(this.iterator());
	}
}