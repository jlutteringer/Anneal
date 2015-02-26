package org.alloy.metal.utility;

@FunctionalInterface
public interface ExceptionOperation {
	public void apply() throws Exception;
}