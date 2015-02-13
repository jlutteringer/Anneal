package org.alloy.metal.function;

import java.util.Iterator;
import java.util.NoSuchElementException;

import org.alloy.metal.function.Transducer.CompletingReducer;
import org.alloy.metal.function.Transducer.Reducer;
import org.alloy.metal.iteration.GeneratingIterable;
import org.alloy.metal.iteration.SingleEntryIterator;

public class Transducers {
	public static class ReductionIterator<R, T> extends SingleEntryIterator<R> {
		private CompletingReducer<R, ? super T> reducingFunction;
		private Iterator<T> input;
		private R result;
		boolean complete = false;

		public ReductionIterator(CompletingReducer<R, ? super T> reducingFunction, Iterator<T> input, R initial) {
			super();
			this.result = initial;
			this.reducingFunction = reducingFunction;
			this.input = input;
		}

		@Override
		protected R fetch() throws NoSuchElementException {
			if (!complete && input.hasNext()) {
				while (input.hasNext()) {
					Reduction<R> reduction = reducingFunction.reduce(result, input.next());
					if (reduction.isReductionHalted()) {
						complete = true;
					}
					if (!reduction.isPartialReduction()) {
						result = reduction.getValue();
						return result;
					}

					if (complete) {
						break;
					}
				}
			}

			Reduction<R> reduction = Reduction.incomplete(result);
			while (reduction.isPartialReduction() && !reduction.isReductionHalted()) {
				reduction = reducingFunction.complete(result);
			}

			if (!reduction.isPartialReduction()) {
				result = reduction.getValue();
				return result;
			}

			return SingleEntryIterator.stop();
		}
	}

	private static <R, T> R reduce(CompletingReducer<R, ? super T> reducingFunction, R result, Iterator<T> input) {
		R ret = result;
		while (input.hasNext()) {
			Reduction<R> reduction = reducingFunction.reduce(ret, input.next());
			ret = reduction.getValue();

			if (reduction.isReductionHalted()) {
				break;
			}
		}

		boolean completionHalted = false;
		while (!completionHalted) {
			Reduction<R> reduction = reducingFunction.complete(ret);
			ret = reduction.getValue();

			if (reduction.isReductionHalted()) {
				completionHalted = true;
			}
		}

		return ret;
	}

	public static <R, A, B> R transduce(Transducer<A, B> transducer, Iterator<B> input, R initial, Reducer<R, ? super A> reducer) {
		CompletingReducer<R, B> transducedFunction = transducer.apply(baseReducer(reducer));
		return reduce(transducedFunction, initial, input);
	}

	public static <R, A, B> Iterator<R> transduceDeferred(Transducer<A, B> transducer, Iterator<B> input, R initial, Reducer<R, ? super A> reducer) {
		CompletingReducer<R, B> transducedFunction = transducer.apply(baseReducer(reducer));
		return new ReductionIterator<>(transducedFunction, input, initial);
	}

	public static <R, A, B> Iterable<R> transduceDeferred(Transducer<A, B> transducer, Iterable<B> input, R initial, Reducer<R, ? super A> reducer) {
		return new GeneratingIterable<R>(() -> {
			return transduceDeferred(transducer, input.iterator(), initial, reducer);
		});
	}

	public static <R, A, B> Iterator<R> transduceDeferred(Transducer<R, B> transducer, Iterator<B> input) {
		CompletingReducer<R, B> transducedFunction = transducer.apply(baseReducer((result, value) -> value));
		return new ReductionIterator<>(transducedFunction, input, null);
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