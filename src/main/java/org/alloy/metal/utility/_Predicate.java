package org.alloy.metal.utility;

import java.util.function.Predicate;

import org.alloy.metal.collections.list.MutableList;
import org.alloy.metal.collections.list._Lists;
import org.alloy.metal.domain.Identifiable;
import org.alloy.metal.equality.SymmetricEqualitor;

public class _Predicate {
	@SafeVarargs
	public static <T> Predicate<T> and(Predicate<T>... predicates) {
		return (object) -> {
			for (Predicate<T> predicate : predicates) {
				if (!predicate.test(object)) {
					return false;
				}
			}
			return true;
		};
	}

	public static <T> Predicate<T> isDefined() {
		return (object) -> {
			if (object != null) {
				return true;
			}
			return false;
		};
	}

	public static <T> Predicate<Predicate<T>> reverse(T element) {
		return (input) -> input.test(element);
	}

	public static <T> PredicateBuilder<T> build(Class<T> clazz) {
		return new PredicateBuilder<T>();
	}

	public static <T extends Identifiable> Predicate<T> matchId(Long id) {
		return (identifiable) -> identifiable.getId().equals(id);
	}

	public static <T> Predicate<T> matchAll() {
		return (element) -> true;
	}

	public static <T> Predicate<T> matchSeen(SymmetricEqualitor<T> equality) {
		MutableList<T> seen = _Lists.list();
		return (element) -> {
			if (seen.filter((seenElement) -> equality.apply(seenElement, element)).isEmpty()) {
				return false;
			}
			seen.add(element);
			return true;
		};
	}

	public static class PredicateBuilder<T> {
		public PredicateBuilder<T> and(Predicate<T> predicate) {
			// FUTURE Auto-generated method stub
			return null;
		}

		public PredicateBuilder<T> or(Predicate<T> predicate) {
			// FUTURE Auto-generated method stub
			return null;
		}

		public PredicateBuilder<T> continueIf(Predicate<T> predicate) {
			// FUTURE Auto-generated method stub
			return null;
		}

		public PredicateBuilder<T> stopIf(Predicate<T> predicate) {
			// FUTURE Auto-generated method stub
			return null;
		}

		public boolean test(T objectToTest) {
			// FUTURE Auto-generated method stub
			return false;
		}

		public Predicate<T> finish() {
			return null;
		}
	}

	public static <T> Predicate<T> not(Predicate<T> predicate) {
		return (item) -> !predicate.test(item);
	}
}