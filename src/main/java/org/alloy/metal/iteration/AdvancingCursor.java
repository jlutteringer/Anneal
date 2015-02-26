package org.alloy.metal.iteration;

import java.util.NoSuchElementException;

import org.alloy.metal.domain.Value;

public abstract class AdvancingCursor<T> implements Cursor<T> {
	private Value<T> value = Value.none();

	@Override
	public boolean hasNext() {
		if (value.isDefined()) {
			return true;
		}

		return this.tryAdvance(this::setValue);
	}

	@Override
	public T next() {
		if (value.isDefined()) {
			return this.getAndClear();
		}

		if (!this.tryAdvance(this::setValue)) {
			throw new NoSuchElementException();
		}

		return this.getAndClear();
	}

	private T getAndClear() {
		T unboxed = value.get();
		value = Value.none();
		return unboxed;
	}

	private void setValue(T value) {
		this.value = Value.of(value);
	}
}
