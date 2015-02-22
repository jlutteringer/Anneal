package org.alloy.metal.transducer;

import java.util.NoSuchElementException;
import java.util.Spliterator;

import org.alloy.metal.iteration.FetchingIterator;
import org.alloy.metal.iteration.FetchingSpliterator;
import org.alloy.metal.transducer.Transducer.CompletingReducer;

public class ReductionSpliterator<R, T> extends FetchingSpliterator<R> {
	private CompletingReducer<R, ? super T> reducingFunction;
	private Spliterator<T> input;
	private Reduction<R> result;
	boolean complete = false;

	public ReductionSpliterator(CompletingReducer<R, ? super T> reducingFunction, Spliterator<T> input, R initial) {
		this.result = Reduction.normal(initial);
		this.reducingFunction = reducingFunction;
		this.input = input;
	}

	@Override
	protected R fetch() throws NoSuchElementException {
		if (!complete) {
			boolean advanced = true;

			while (advanced) {
				advanced = input.tryAdvance((value) -> {
					result = reducingFunction.reduce(result.getValue(), value);
				});

				if (advanced) {
					if (result.isReductionHalted()) {
						complete = true;
					}

					if (!result.isPartialReduction()) {
						return result.getValue();
					}

					if (complete) {
						break;
					}
				}
			}
		}

		result = Reduction.incomplete(result.getValue());
		while (result.isPartialReduction() && !result.isReductionHalted()) {
			result = reducingFunction.complete(result.getValue());
		}

		if (!result.isPartialReduction()) {
			return result.getValue();
		}

		return FetchingIterator.stop();
	}

	@Override
	public Spliterator<R> trySplit() {
		Spliterator<T> split = input.trySplit();
		if (split != null) {
			return new ReductionSpliterator<R, T>(reducingFunction, split, result.getValue());
		}
		return null;
	}

	@Override
	public long estimateSize() {
		return input.estimateSize();
	}

	@Override
	public int characteristics() {
		return input.characteristics();
	}
}