package org.alloy.metal.domain;

public class Container<T> {
	private Value<T> value = Value.none();

	public T getValue() {
		return value.get();
	}

	public void setValue(T value) {
		this.value = Value.of(value);
	}

	public void clear() {
		this.value = Value.none();
	}

	public static <T> Container<T> create() {
		return new Container<T>();
	}

	public static <T> Container<T> create(T value) {
		Container<T> container = create();
		container.setValue(value);
		return container;
	}
}