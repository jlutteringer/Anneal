package org.alloy.metal.flow;

import java.util.Iterator;

import org.alloy.metal.function._Functions;
import org.alloy.metal.iteration._Iteration;
import org.alloy.metal.iteration.cursor.Cursor;
import org.alloy.metal.transducer.Transducer;
import org.alloy.metal.transducer.Transducer.Reducer;

public class TransductionPipeline<T, N> implements Flow<T> {
	private Iterator<N> iterator;
	private Transducer<T, N> transducer;

	public TransductionPipeline(Iterator<N> iterator, Transducer<T, N> transducer) {
		this.transducer = transducer;
		this.iterator = iterator;
	}

	@Override
	public Flow<T> parallelFlow() {
		throw new UnsupportedOperationException();
	}

	@Override
	public <R> Flow<R> compose(Transducer<R, T> transducer) {
		return new TransductionPipeline<R, N>(iterator, this.transducer.compose(transducer));
	}

	@Override
	public Cursor<T> cursor() {
		return _Iteration.cursor(transducer.transduceDeferred(iterator));
	}

	@Override
	public <R> R collect(R initial, Reducer<R, ? super T> reducer) {
		return transducer.transduce(iterator, initial, reducer);
	}

	public static class BaseTransductionPipeline<T> extends TransductionPipeline<T, T> {
		public BaseTransductionPipeline(Iterator<T> iterator) {
			super(iterator, _Functions.identity());
		}
	}
}