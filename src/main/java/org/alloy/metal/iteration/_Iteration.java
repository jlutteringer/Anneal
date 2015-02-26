package org.alloy.metal.iteration;

import java.util.Iterator;
import java.util.Optional;
import java.util.function.Supplier;

import org.alloy.metal.domain.Advancer;

public class _Iteration {
	public static <T> Iterator<T> iterator(Advancer<T> advancer) {
		return new DelegatingAdvancingIterator<T>(advancer);
	}

	public static <T> AIterable<T> iterable(Supplier<Advancer<T>> supplier) {
		return new GeneratingIterable<T>(() -> {
			return iterator(supplier.get());
		});
	}

	public static <T> Optional<T> first(Iterable<T> iterable) {
		return first(iterable.iterator());
	}

	public static <T> Optional<T> first(Cursor<T> cursor) {
		if (!cursor.hasNext()) {
			return Optional.empty();
		}
		else {
			return Optional.of(cursor.next());
		}
	}

	public static <T> Optional<T> first(Iterator<T> iterator) {
		if (!iterator.hasNext()) {
			return Optional.empty();
		}
		else {
			return Optional.of(iterator.next());
		}
	}

	public static <T> Cursor<T> cursor(Iterator<T> iterator) {
		return new IteratorCursor<T>(iterator);
	}
}