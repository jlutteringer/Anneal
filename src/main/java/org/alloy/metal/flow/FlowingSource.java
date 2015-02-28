package org.alloy.metal.flow;

import java.util.Comparator;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;

public interface FlowingSource<T> extends Source<T> {
	public Flow<T> flow();

	@Override
	public default Flow<T> filter(Predicate<? super T> filter) {
		return this.flow().filter(filter);
	}

	@Override
	public default Flow<T> take(long numberOfElements, boolean strict) {
		return this.flow().take(numberOfElements, strict);
	}

	@Override
	public default Flow<T> sort(Comparator<T> comarator) {
		return this.flow().sort(comarator);
	}

	@Override
	public default <R> Flow<R> map(Function<T, R> mappingFunction) {
		return this.flow().map(mappingFunction);
	}

	@Override
	public default <R> Flow<R> flatMap(Function<T, Source<R>> mappingFunction) {
		return this.flow().flatMap(mappingFunction);
	}

	@Override
	public default Flow<Iterable<T>> partition(long size) {
		return this.flow().partition(size);
	}

	@Override
	default void forEach(Consumer<? super T> action) {
		this.flow().forEach(action);
	}
}
