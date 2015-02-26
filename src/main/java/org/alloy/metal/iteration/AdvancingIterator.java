package org.alloy.metal.iteration;

import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.function.Consumer;

import org.alloy.metal.domain.Advancer;
import org.alloy.metal.domain.Value;

public abstract class AdvancingIterator<T> implements Iterator<T>, Advancer<T> {
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

	@Override
	public void remove() {
		throw new UnsupportedOperationException();
	}

	@Override
	public abstract boolean tryAdvance(Consumer<? super T> action);
}