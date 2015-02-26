package org.alloy.metal.utility;

@FunctionalInterface
public interface ExceptionSupplier<T, N extends Exception> {
	public T next() throws N;
}