package org.alloy.metal.transducer;

public class Reduction<T> {
	private T value;
	private boolean partialReduction;
	private boolean reductionHalted;
	private boolean inputNeeded;

	public Reduction(T value, boolean partialReduction, boolean reductionHalted, boolean inputNeeded) {
		this.value = value;
		this.partialReduction = partialReduction;
		this.reductionHalted = reductionHalted;
		this.inputNeeded = inputNeeded;
	}

	public T getValue() {
		return value;
	}

	public boolean isPartialReduction() {
		return partialReduction;
	}

	public boolean isReductionHalted() {
		return reductionHalted;
	}

	public boolean isInputNeeded() {
		return inputNeeded;
	}

	public static <T> Reduction<T> incomplete(T value) {
		return new Reduction<T>(value, true, false, true);
	}

	public static <T> Reduction<T> haltIncomplete(T value) {
		return new Reduction<T>(value, true, true, true);
	}

	public static <T> Reduction<T> normal(T value) {
		return new Reduction<T>(value, false, false, true);
	}

	public static <T> Reduction<T> halt(Reduction<T> value) {
		return new Reduction<T>(value.getValue(), value.isPartialReduction(), true, value.isInputNeeded());
	}

	public static <T> Reduction<T> witholdInput(Reduction<T> value) {
		return new Reduction<T>(value.getValue(), value.isPartialReduction(), value.isReductionHalted(), false);
	}
}