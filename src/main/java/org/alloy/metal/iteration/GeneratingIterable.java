package org.alloy.metal.iteration;

import java.util.Iterator;
import java.util.function.Supplier;

import org.alloy.metal.iteration.cursor.Cursor;

public class GeneratingIterable<T> extends IterableWithToString<T> {

	private Supplier<Cursor<T>> supplier;

	public GeneratingIterable(Supplier<Cursor<T>> supplier) {
		this.supplier = supplier;
	}

	@Override
	public Cursor<T> cursor() {
		return supplier.get();
	}

	@Override
	public Iterator<T> iterator() {
		return cursor().toIterator();
	}
}