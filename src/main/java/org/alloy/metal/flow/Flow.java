package org.alloy.metal.flow;

import java.util.Comparator;
import java.util.function.Function;
import java.util.function.Predicate;

import org.alloy.metal.function._Functions;

public interface Flow<T> extends Source<T> {
	@Override
	public default Flow<T> flow() {
		return this;
	}

	public default Flow<T> parallelFlow() {
		throw new UnsupportedOperationException();
	}

	@Override
	public default Flow<T> filter(Predicate<T> filter) {
		return this.compose(_Functions.filter(filter));
	}

	@Override
	public default Flow<T> take(long numberOfElements, boolean strict) {
		return this.compose(_Functions.take(numberOfElements, strict));
	}

	@Override
	public default Flow<T> sort(Comparator<T> comparator) {
		return this.compose(_Functions.sort(comparator));
	}

	@Override
	public default <R> Flow<R> map(Function<T, R> mappingFunction) {
		return this.compose(_Functions.map(mappingFunction));
	}

	@Override
	public default <R> Flow<R> flatMap(Function<T, Source<R>> mappingFunction) {
		return this.compose(_Functions.flatMap(mappingFunction));
	}

	@Override
	public default Flow<Iterable<T>> partition(long size) {
		return this.compose(_Functions.partition((int) size));
	}
}