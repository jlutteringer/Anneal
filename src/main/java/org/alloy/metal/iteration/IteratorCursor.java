package org.alloy.metal.iteration;

import java.util.Iterator;
import java.util.Spliterator;
import java.util.function.Consumer;

import org.alloy.metal.domain.NotImplementedException;

public class IteratorCursor<T> extends AdvancingCursor<T> {
	private Iterator<T> iterator;

	public IteratorCursor(Iterator<T> iterator) {
		this.iterator = iterator;
	}

	@Override
	public boolean tryAdvance(Consumer<? super T> action) {
		if (!iterator.hasNext()) {
			return false;
		}

		action.accept(iterator.next());
		return true;
	}

	@Override
	public Iterator<T> toIterator() {
		return iterator;
	}

	@Override
	public Spliterator<T> toSpliterator() {
		throw new NotImplementedException();
	}
}
