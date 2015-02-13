package org.alloy.metal.function;

import java.util.Iterator;

import org.alloy.metal.function.Transducer.Reducer;

public class TransductionContext<T, N> implements Flow<T> {
	private Iterator<N> iterator;
	private Transducer<T, N> internalTransducer;

	public TransductionContext(Iterator<N> iterator, Transducer<T, N> transducer) {
		this.iterator = iterator;
		internalTransducer = transducer;
	}

	@Override
	public <R> Flow<R> compose(Transducer<R, T> transducer) {
		return new TransductionContext<R, N>(iterator, internalTransducer.compose(transducer));
	}

	@Override
	public Iterator<T> iterator() {
		return internalTransducer.transduceDeferred(iterator);
	}

	@Override
	public <R> R collect(R initial, Reducer<R, ? super T> reducer) {
		return internalTransducer.transduce(iterator, initial, reducer);
	}
}