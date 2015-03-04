package org.alloy.metal.reflection;

import java.util.List;
import java.util.function.Predicate;

import org.alloy.metal.collections.list._Lists;
import org.alloy.metal.flow.Flow;
import org.alloy.metal.flow.Source;

public class _Class {
	public static void intialize(Class<?> clazz) {
		try {
			Class.forName(clazz.getName());
		} catch (ClassNotFoundException e) {
			// Do nothing
		}
	}

	public static Flow<Class<?>> getTypes(Object... objects) {
		return getTypes(_Lists.list(objects));
	}

	public static Flow<Class<?>> getTypes(Source<Object> objects) {
		return objects.<Class<?>> map((object) -> object.getClass());
	}

	public static String stringify(Class<?> clazz) {
		return clazz.getName();
	}

	public static Predicate<Object> exclude(List<Class<?>> excludedClasses) {
		return (object) -> {
			for (Class<?> clazz : excludedClasses) {
				if (clazz.isAssignableFrom(object.getClass())) {
					return false;
				}
			}
			return true;
		};
	}

	public static Predicate<Object> include(List<Class<?>> includedClasses) {
		return exclude(includedClasses).negate();
	}
}