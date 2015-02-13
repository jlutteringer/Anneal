package org.alloy.metal.function;

public class Reduction<T> {
	private T value;
	private boolean partialReduction;
	private boolean reductionHalted;

	public Reduction(T value, boolean partialReduction, boolean reductionHalted) {
		this.value = value;
		this.partialReduction = partialReduction;
		this.reductionHalted = reductionHalted;
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

	public static <T> Reduction<T> incomplete(T value) {
		return new Reduction<T>(value, true, false);
	}

	public static <T> Reduction<T> haltIncomplete(T value) {
		return new Reduction<T>(value, true, true);
	}

	public static <T> Reduction<T> normal(T value) {
		return new Reduction<T>(value, false, false);
	}

	public static <T> Reduction<T> halt(Reduction<T> value) {
		return new Reduction<T>(value.getValue(), value.isPartialReduction(), true);
	}
}