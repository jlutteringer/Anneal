package org.alloy.metal.collections.map;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.function.Supplier;

import org.alloy.metal.flow.Source;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Multimap;
import com.google.common.collect.Multimaps;

public class _Maps {
	public static <T, N> MutableMap<T, N> map() {
		return wrap(Maps.newHashMap());
	}

	public static <T, N> MutableMap<T, N> map(Function<N, T> keyExtractor, Source<N> iterable) {
		MutableMap<T, N> map = map();
		iterable.forEach((item) -> {
			T key = keyExtractor.apply(item);
			if (map.containsKey(key)) {
				throw new RuntimeException("Duplicate key resolved building map");
			}

			map.put(key, item);
		});

		return map;
	}

	public static <T, N> MutableMap<T, N> defaultMap(Supplier<N> supplier) {
		return wrap(new DefaultHashMap<>(supplier));
	}

	public static <T, N> MutableMap<T, N> wrap(Map<T, N> map) {
		return new MutableMapAdapter<T, N>(map);
	}

	public static <T, N> MutableMultimap<T, N> wrap(Multimap<T, N> map) {
		return new MutableMultimapAdapter<T, N>(map);
	}

	public static <T, N> MutableMultimap<T, N> multiMap() {
		return wrap(Multimaps.newListMultimap(
				Maps.newLinkedHashMap(),
				new com.google.common.base.Supplier<List<N>>() {
					@Override
					public List<N> get() {
						return Lists.newArrayList();
					}
				}));
	}

	public static <T, N> MutableMultimap<T, N> multiMap(Function<N, T> keyExtractor, Source<N> source) {
		MutableMultimap<T, N> map = multiMap();
		source.forEach((item) -> {
			map.put(keyExtractor.apply(item), item);
		});
		return map;
	}

//	public static <T, N> Supplier<Map<T, N>> mapSupplier() {
//	return () -> Maps.newHashMap();
//}
//
//public static <T, N> Supplier<Map<T, N>> concurrentMapSupplier() {
//	return () -> Maps.newConcurrentMap();
//}
//
//public static <T, N> Map<T, N> map(Function<N, T> keyExtractor, Iterable<N> iterable) {
//	Map<T, N> map = map();
//	iterable.forEach((item) -> {
//		T key = keyExtractor.apply(item);
//		if (map.containsKey(key)) {
//			throw new RuntimeException("Duplicate key resolved building map");
//		}
//
//		map.put(key, item);
//	});
//
//	return map;
//}
//
//
//public static <T, N> Multimap<T, N> multiMap(Function<N, T> keyExtractor, Iterable<N> iterable) {
//	Multimap<T, N> map = multiMap();
//	iterable.forEach((item) -> {
//		map.put(keyExtractor.apply(item), item);
//	});
//
//	return map;
//}
}