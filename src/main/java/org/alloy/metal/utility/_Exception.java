package org.alloy.metal.utility;

import java.util.Optional;

import com.google.common.base.Throwables;

public class _Exception {
	public static void propagate(ExceptionOperation operation) {
		try {
			operation.apply();
		} catch (Exception e) {
			throw Throwables.propagate(e);
		}
	}

	public static <T> T propagate(ExceptionSupplier<T, Exception> operation) {
		try {
			return operation.next();
		} catch (Exception e) {
			throw Throwables.propagate(e);
		}
	}

	public static void ignore(ExceptionOperation operation) {
		try {
			operation.apply();
		} catch (Exception e) {
		}
	}

	public static <T> Optional<T> ignore(ExceptionSupplier<T, Exception> operation) {
		try {
			return Optional.of(operation.next());
		} catch (Exception e) {
			return Optional.empty();
		}
	}
}