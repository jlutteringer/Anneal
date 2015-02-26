package org.alloy.metal.iteration;

import java.util.Comparator;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;

import org.alloy.metal.flow.Completable;
import org.alloy.metal.flow.Flow;
import org.alloy.metal.flow.Source;
import org.alloy.metal.flow.TransductionPipeline.BaseTransductionPipeline;
import org.alloy.metal.transducer.Transducer;

public interface AIterable<T> extends Iterable<T>, Source<T>, Completable<T> {
	@Override
	public default Flow<T> flow() {
		return new BaseTransductionPipeline<T>(this.iterator());
	}

	@Override
	public default Flow<T> filter(Predicate<T> filter) {
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
	public default <R> Flow<R> compose(Transducer<R, T> transducer) {
		return this.flow().compose(transducer);
	}

	@Override
	default void forEach(Consumer<? super T> action) {
		this.flow().forEach(action);
	}

	@Override
	public default Cursor<T> cursor() {
		return _Iteration.cursor(this.iterator());
	}
}