package org.alloy.metal.utility;

import java.util.function.Consumer;

@FunctionalInterface
public interface ExceptionProvider<T, N extends Exception> {
	public void with(Consumer<T> consumer) throws N;
}