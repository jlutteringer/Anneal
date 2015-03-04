package org.alloy.metal.iteration;

import java.util.Iterator;
import java.util.Optional;
import java.util.function.Supplier;

import org.alloy.metal.domain.Advancer;
import org.alloy.metal.equality.Equalitor;
import org.alloy.metal.iteration.cursor.Cursor;
import org.alloy.metal.iteration.cursor.DelegatingAdvancingCursor;

public class _Iteration {
	public static <T> Cursor<T> cursor(Advancer<T> advancer) {
		return new DelegatingAdvancingCursor<T>(advancer);
	}

	public static <T> Cursor<T> cursor(Iterator<T> iterator) {
		return new DelegatingAdvancingCursor<T>(advancer(iterator));
	}

	public static <T> Cursor<T> cursor(Iterable<T> iterable) {
		return cursor(iterable.iterator());
	}

	public static <T> Cursor<T> emptyCursor() {
		return cursor((consumer) -> false);
	}

	public static <T> AIterable<T> iterable(Supplier<Advancer<T>> supplier) {
		return new GeneratingIterable<T>(() -> {
			return cursor(supplier.get());
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

	public static <T> Optional<T> single(Cursor<T> cursor) {
		if (!cursor.hasNext()) {
			return Optional.empty();
		}
		else {
			T next = cursor.next();
			if (cursor.hasNext()) {
				throw new RuntimeException("Cursor with multiple results found when we expected a single result!");
			}

			return Optional.of(next);
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

	public static <T, N> boolean contains(Cursor<T> cursor, N value, Equalitor<T, N> equality) {
		while (cursor.hasNext()) {
			if (equality.apply(cursor.next(), value)) {
				return true;
			}
		}
		return false;
	}

	public static <T> Advancer<T> advancer(Iterator<T> iterator) {
		return (consumer) -> {
			if (iterator.hasNext()) {
				consumer.accept(iterator.next());
				return true;
			}
			else {
				return false;
			}
		};
	}
}