package org.alloy.metal.utility;

import java.util.function.Predicate;

import org.alloy.metal.collections.list._Lists;

public class _Precondition {
	@SafeVarargs
	public static <T> void check(Predicate<T> filter, T... objects) {
		if (!_Lists.list(objects).filter(filter).isEmpty()) {
			throw new IllegalArgumentException();
		}
	}

	@SafeVarargs
	public static <T> void notNull(T... objects) {
		_Precondition.check(_Predicate.isDefined().negate(), objects);
	}
}