package org.alloy.metal.iteration;

import java.util.NoSuchElementException;
import java.util.Spliterator;
import java.util.function.Consumer;

public abstract class FetchingSpliterator<T> implements Spliterator<T> {
	@Override
	public boolean tryAdvance(Consumer<? super T> action) {
		try {
			action.accept(this.fetch());
			return true;
		} catch (NoSuchElementException e) {
			return false;
		}
	}

	protected abstract T fetch() throws NoSuchElementException;
}