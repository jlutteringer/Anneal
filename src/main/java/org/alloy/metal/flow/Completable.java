package org.alloy.metal.flow;

import java.util.Optional;
import java.util.function.Consumer;

import org.alloy.metal.collections.list.MutableList;
import org.alloy.metal.collections.list._Lists;
import org.alloy.metal.equality._Equality;
import org.alloy.metal.iteration._Iteration;
import org.alloy.metal.iteration.cursor.Cursor;
import org.alloy.metal.transducer.Transducer.Reducer;

public interface Completable<T> {
	default void forEach(Consumer<? super T> action) {
		this.cursor().forEach(action);
	}

	public default MutableList<T> collectList() {
		return collect(_Lists.<T> empty(), (result, value) -> {
			result.add(value);
			return result;
		});
	}

	public default T firstStrict() {
		return first().get();
	}

	public default Optional<T> first() {
		return _Iteration.first(this.cursor());
	}

	public default T singleStrict() {
		return single().get();
	}

	public default Optional<T> single() {
		return _Iteration.single(this.cursor());
	}

	public default <R> R collect(R initial, Reducer<R, ? super T> reducer) {
		Cursor<T> iterator = this.cursor();
		while (iterator.hasNext()) {
			initial = reducer.reduce(initial, iterator.next());
		}
		return initial;
	}

	public default boolean isEmpty() {
		return !first().isPresent();
	}

	public default boolean contains(T value) {
		return _Iteration.contains(this.cursor(), value, _Equality.natural());
	}

	public Cursor<T> cursor();
}