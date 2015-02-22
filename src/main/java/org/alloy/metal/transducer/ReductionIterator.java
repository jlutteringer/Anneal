package org.alloy.metal.transducer;

import java.util.Iterator;
import java.util.NoSuchElementException;

import org.alloy.metal.iteration.FetchingIterator;
import org.alloy.metal.transducer.Transducer.CompletingReducer;

public class ReductionIterator<R, T> extends FetchingIterator<R> {
	private CompletingReducer<R, ? super T> reducingFunction;
	private Iterator<T> input;
	private R result;
	boolean complete = false;

	public ReductionIterator(CompletingReducer<R, ? super T> reducingFunction, Iterator<T> input, R initial) {
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

		return FetchingIterator.stop();
	}
}