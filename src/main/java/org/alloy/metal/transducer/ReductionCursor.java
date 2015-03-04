package org.alloy.metal.transducer;

import java.util.Iterator;
import java.util.function.Consumer;

import org.alloy.metal.iteration.cursor.AdvancingCursor;
import org.alloy.metal.transducer.Transducer.CompletingReducer;

public class ReductionCursor<R, T> extends AdvancingCursor<R> {
	private CompletingReducer<R, ? super T> reducingFunction;
	private Iterator<T> input;
	private R result;
	boolean complete = false;
	boolean inputNeeded = true;

	public ReductionCursor(CompletingReducer<R, ? super T> reducingFunction, Iterator<T> input, R initial) {
		this.result = initial;
		this.reducingFunction = reducingFunction;
		this.input = input;
	}

	@Override
	public boolean tryAdvance(Consumer<? super R> action) {
		if (!complete && input.hasNext()) {
			while (input.hasNext()) {
				T inputValue = null;
				if (inputNeeded) {
					inputValue = input.next();
				}

				Reduction<R> reduction = reducingFunction.reduce(result, inputValue);
				inputNeeded = reduction.isInputNeeded();

				if (reduction.isReductionHalted()) {
					complete = true;
				}
				if (!reduction.isPartialReduction()) {
					result = reduction.getValue();
					action.accept(result);
					return true;
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
			action.accept(result);
			return true;
		}

		return false;
	}
}