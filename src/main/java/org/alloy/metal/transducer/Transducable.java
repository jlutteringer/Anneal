package org.alloy.metal.transducer;

import java.util.Comparator;
import java.util.function.Function;
import java.util.function.Predicate;

import org.alloy.metal.flow.Flow;
import org.alloy.metal.flow.Source;

public interface Transducable<T> {
	public Flow<T> filter(Predicate<T> filter);

	public Flow<T> take(long numberOfElements, boolean strict);

	public Flow<T> sort(Comparator<T> comarator);

	public <R> Flow<R> map(Function<T, R> mappingFunction);

	public <R> Flow<R> flatMap(Function<T, Source<R>> mappingFunction);

	public Flow<Iterable<T>> partition(long size);

	public <R> Flow<R> compose(Transducer<R, T> transducer);
}