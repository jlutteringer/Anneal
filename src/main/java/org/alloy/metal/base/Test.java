package org.alloy.metal.base;

import java.util.List;

import org.alloy.metal.collections.list.AList;
import org.alloy.metal.collections.list._Lists;
import org.alloy.metal.flow.Flow;
import org.alloy.metal.function._Functions;

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

		AList<Iterable<Integer>> result =
				integers.flow()
						.parallel()
						.filter((element) -> true)
						.filter((element) -> true)
						.compose(_Functions.filter((element) -> true))
						.compose(_Functions.partition(3))
						.collectList();

		Flow<Iterable<Integer>> flow = integers.flow()
				.filter((element) -> true)
				.filter((element) -> true)
				.compose(_Functions.filter((element) -> true))
				.compose(_Functions.partition(3));

		result.forEach(System.out::println);
	}
}