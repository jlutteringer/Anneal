package org.alloy.metal.domain;

import java.util.function.Consumer;

@FunctionalInterface
public interface Advancer<T> {
	public abstract boolean tryAdvance(Consumer<? super T> action);
}