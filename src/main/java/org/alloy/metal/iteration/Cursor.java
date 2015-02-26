package org.alloy.metal.iteration;

import java.util.Iterator;
import java.util.Spliterator;
import java.util.function.Consumer;

import org.alloy.metal.domain.Advancer;

public interface Cursor<T> extends Advancer<T> {
	default void forEachRemaining(Consumer<? super T> action) {
		while (tryAdvance(action)) {
			// do nothing
		}
	}

	public boolean hasNext();

	public T next();

	public Iterator<T> toIterator();

	public Spliterator<T> toSpliterator();
}