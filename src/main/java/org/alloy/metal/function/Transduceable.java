package org.alloy.metal.function;

import java.util.Comparator;
import java.util.function.Function;
import java.util.function.Predicate;

public interface Transduceable<T> extends Iterable<T> {
	public default Flow<T> filter(Predicate<T> filter) {
		return this.compose(_Function.filter(filter));
	}

	public default Flow<T> take(long numberOfElements, boolean strict) {
		return this.compose(_Function.take(numberOfElements, strict));
	}

	public default Flow<T> first(boolean strict) {
		return this.compose(_Function.first(strict));
	}

	public default Flow<T> sort(Comparator<T> comarator) {
		return this.compose(_Function.sort(comarator));
	}

	public default <R> Flow<R> map(Function<T, R> mappingFunction) {
		return this.compose(_Function.map(mappingFunction));
	}

	public default Flow<Iterable<T>> partition(long size) {
		return this.compose(_Function.partition((int) size));
	}

	public default Flow<T> parallel() {
		return this.compose(_Function.parallel());
	}

	public <R> Flow<R> compose(Transducer<R, T> transducer);
}