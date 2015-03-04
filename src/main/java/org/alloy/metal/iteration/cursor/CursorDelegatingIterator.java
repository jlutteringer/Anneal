package org.alloy.metal.iteration.cursor;

import java.util.Iterator;

public class CursorDelegatingIterator<T> implements Iterator<T> {
	private Cursor<T> cursor;

	public CursorDelegatingIterator(Cursor<T> cursor) {
		this.cursor = cursor;
	}

	@Override
	public boolean hasNext() {
		return cursor.hasNext();
	}

	@Override
	public T next() {
		return cursor.next();
	}
}