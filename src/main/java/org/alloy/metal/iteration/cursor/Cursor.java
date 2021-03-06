package org.alloy.metal.iteration.cursor;

import java.util.Iterator;
import java.util.Spliterator;
import java.util.function.Consumer;

import org.alloy.metal.domain.Advancer;
import org.alloy.metal.domain.NotImplementedException;
import org.alloy.metal.flow.Flow;
import org.alloy.metal.flow.FlowingSource;
import org.alloy.metal.flow.TransductionPipeline.BaseTransductionPipeline;
import org.alloy.metal.transducer.Transducer;

public interface Cursor<T> extends Advancer<T>, FlowingSource<T> {
	@Override
	default void forEach(Consumer<? super T> action) {
		while (tryAdvance(action)) {
			// do nothing
		}
	}

	public boolean hasNext();

	public T next();

	public default Iterator<T> toIterator() {
		return new CursorDelegatingIterator<T>(this);
	}

	public default Spliterator<T> toSpliterator() {
		// FUTURE
		throw new NotImplementedException();
	}

	@Override
	public default Flow<T> flow() {
		return new BaseTransductionPipeline<T>(this.toIterator());
	}

	@Override
	public default Cursor<T> cursor() {
		return this;
	}

	@Override
	public default <R> Flow<R> compose(Transducer<R, T> transducer) {
		return this.flow().compose(transducer);
	}
}