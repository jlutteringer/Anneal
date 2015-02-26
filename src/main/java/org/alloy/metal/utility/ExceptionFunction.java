package org.alloy.metal.utility;

@FunctionalInterface
public interface ExceptionFunction<T, R> {
	public R apply(T t) throws Exception;
}