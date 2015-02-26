package org.alloy.metal.utility;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

public class Stringifier {
	static {
		ReflectionToStringBuilder.setDefaultStyle(ToStringStyle.SHORT_PREFIX_STYLE);
	}

	private Object target;
	private boolean useReflection = false;

	public Stringifier(Object object) {
		this.target = object;
	}

	public String build() {
		return new ReflectionToStringBuilder(target).build();
	}

	public Stringifier useReflection() {
		useReflection = true;
		return this;
	}
}