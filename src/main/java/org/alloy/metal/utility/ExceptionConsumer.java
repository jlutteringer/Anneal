package org.alloy.metal.utility;

@FunctionalInterface
public interface ExceptionConsumer<T, N extends Exception> {
	public void accept(T t) throws N;
}