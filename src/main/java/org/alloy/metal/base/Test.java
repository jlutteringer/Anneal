package org.alloy.metal.base;

import java.util.List;

import org.alloy.metal.collections.list.AList;
import org.alloy.metal.collections.list._Lists;
import org.alloy.metal.function._Function;

import com.google.common.collect.Lists;

public class Test {
	public static void main(String[] args) {
		List<Integer> standardIntegers = Lists.newArrayList(1, 2, 3, 4, 5);

		standardIntegers.stream().filter((element) -> {
			System.out.println("filter called");
			return true;
		}).filter((element) -> true)
				.iterator();

		AList<Integer> integers = _Lists.list(1, 2, 3, 4, 5);
		AList<Iterable<Integer>> a = integers.filter((element) -> true)
				.filter((element) -> true)
				.compose(_Function.filter((element) -> true))
				.compose(_Function.partition(3))
				.collectList();

		integers.forEach(System.out::println);
	}
}