package org.alloy.metal.iteration;

import java.util.Iterator;
import java.util.NoSuchElementException;

public abstract class FetchingIterator<T> implements Iterator<T> {
	private boolean nextGenerated = false;
	private T next;

	@Override
	public boolean hasNext() {
		if (!nextGenerated) {
			try {
				next = fetch();
				nextGenerated = true;
			} catch (NoSuchElementException e) {
				nextGenerated = false;
			}
		}
		return nextGenerated;
	}

	@Override
	public T next() {
		if (nextGenerated) {
			nextGenerated = false;
			return next;
		}
		else {
			return fetch();
		}
	}

	@Override
	public void remove() {
		throw new UnsupportedOperationException();
	}

	public static <T> T stop() throws NoSuchElementException {
		throw new NoSuchElementException();
	}

	protected abstract T fetch() throws NoSuchElementException;
}
