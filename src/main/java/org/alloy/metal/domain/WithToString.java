package org.alloy.metal.domain;

import org.alloy.metal.string._String;

public abstract class WithToString {
	@Override
	public String toString() {
		return _String.stringify(this).useReflection().build();
	}
}