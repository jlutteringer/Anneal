package org.alloy.metal.transducer;

import java.util.Comparator;
import java.util.function.Function;
import java.util.function.Predicate;

import org.alloy.metal.flow.Flow;
import org.alloy.metal.flow.Source;
import org.alloy.metal.function._Functions;

public interface Transducable<T> {
	public default Flow<T> traverse(Function<T, Source<T>> traversingFunction) {
		return this.compose(_Functions.traverse(traversingFunction));
	}

	public default Flow<T> filter(Predicate<? super T> filter) {
		return this.compose(_Functions.filter(filter));
	}

	public default Flow<T> take(long numberOfElements, boolean strict) {
		return this.compose(_Functions.take(numberOfElements, strict));
	}

	public default Flow<T> sort(Comparator<T> comparator) {
		return this.compose(_Functions.sort(comparator));
	}

	public default <R> Flow<R> map(Function<T, R> mappingFunction) {
		return this.compose(_Functions.map(mappingFunction));
	}

	public default <R> Flow<R> flatMap(Function<T, Source<R>> mappingFunction) {
		return this.compose(_Functions.flatMap(mappingFunction));
	}

	public default Flow<Iterable<T>> partition(long size) {
		return this.compose(_Functions.partition((int) size));
	}

	public <R> Flow<R> compose(Transducer<R, T> transducer);
}