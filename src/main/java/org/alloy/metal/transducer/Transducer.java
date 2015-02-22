package org.alloy.metal.transducer;

import java.util.Iterator;

@FunctionalInterface
public interface Transducer<T, N> {
	public <R> CompletingReducer<R, N> apply(CompletingReducer<R, ? super T> reducer);

	public default <R> R transduce(Iterator<N> input, R initial, Reducer<R, ? super T> reducer) {
		return _Transducers.transduce(this, input, initial, reducer);
	}

	public default <R> Iterator<R> transduceDeferred(Iterator<N> input, R initial, Reducer<R, ? super T> reducer) {
		return _Transducers.transduceDeferred(this, input, initial, reducer);
	}

	public default <R> Iterable<R> transduceDeferred(Iterable<N> input, R initial, Reducer<R, ? super T> reducer) {
		return _Transducers.transduceDeferred(this, input, initial, reducer);
	}

	public default Iterator<T> transduceDeferred(Iterator<N> input) {
		return _Transducers.transduceDeferred(this, input);
	}

	public default Iterable<T> transduceDeferred(Iterable<N> input) {
		return _Transducers.transduceDeferred(this, input);
	}

	public default <A> Transducer<A, N> compose(final Transducer<A, ? super T> right) {
		return new Transducer<A, N>() {
			@Override
			public <R> CompletingReducer<R, N> apply(CompletingReducer<R, ? super A> reducer) {
				return Transducer.this.apply(right.apply(reducer));
			}
		};
	}

	public interface Reducer<T, N> {
		public T reduce(T result, N input);
	}

	public interface CompletingReducer<T, N> {
		public Reduction<T> reduce(T result, N input);

		public Reduction<T> complete(T result);
	}

	public abstract class ReducerOn<T, N> implements CompletingReducer<T, N> {
		private CompletingReducer<T, ?> reducer;

		public ReducerOn(CompletingReducer<T, ?> reducer) {
			this.reducer = reducer;
		}

		@Override
		public Reduction<T> complete(T result) {
			return reducer.complete(result);
		}
	}
}