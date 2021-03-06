package org.alloy.metal.transducer;

import java.util.Iterator;

import org.alloy.metal.iteration.GeneratingIterable;
import org.alloy.metal.iteration.cursor.Cursor;
import org.alloy.metal.transducer.Transducer.CompletingReducer;
import org.alloy.metal.transducer.Transducer.Reducer;

public class _Transducers {
	private static <R, T> R reduce(CompletingReducer<R, ? super T> reducingFunction, Iterator<T> input, R initial) {
		ReductionCursor<R, T> iterator = new ReductionCursor<R, T>(reducingFunction, input, initial);
		R ret = initial;
		while (iterator.hasNext()) {
			ret = iterator.next();
		}
		return ret;
	}

	public static <R, A, B> R transduce(Transducer<A, B> transducer, Iterator<B> input, R initial, Reducer<R, ? super A> reducer) {
		CompletingReducer<R, B> transducedFunction = transducer.apply(baseReducer(reducer));
		return reduce(transducedFunction, input, initial);
	}

	public static <R, A, B> Cursor<R> transduceDeferred(Transducer<A, B> transducer, Iterator<B> input, R initial, Reducer<R, ? super A> reducer) {
		CompletingReducer<R, B> transducedFunction = transducer.apply(baseReducer(reducer));
		return new ReductionCursor<>(transducedFunction, input, initial);
	}

	public static <R, A, B> Iterable<R> transduceDeferred(Transducer<A, B> transducer, Iterable<B> input, R initial, Reducer<R, ? super A> reducer) {
		return new GeneratingIterable<R>(() -> {
			return transduceDeferred(transducer, input.iterator(), initial, reducer);
		});
	}

	public static <R, A, B> Cursor<R> transduceDeferred(Transducer<R, B> transducer, Iterator<B> input) {
		CompletingReducer<R, B> transducedFunction = transducer.apply(baseReducer((result, value) -> value));
		return new ReductionCursor<>(transducedFunction, input, null);
	}

	public static <R, A, B> Iterable<R> transduceDeferred(Transducer<R, B> transducer, Iterable<B> input) {
		return new GeneratingIterable<R>(() -> {
			return transduceDeferred(transducer, input.iterator());
		});
	}

	private static <R, A> CompletingReducer<R, A> baseReducer(Reducer<R, A> reducer) {
		return new CompletingReducer<R, A>() {
			@Override
			public Reduction<R> reduce(R result, A input) {
				return Reduction.normal(reducer.reduce(result, input));
			}

			@Override
			public Reduction<R> complete(R result) {
				return Reduction.haltIncomplete(result);
			}
		};
	}
}