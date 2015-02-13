package org.alloy.metal.function;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.function.Function;
import java.util.function.Predicate;

import com.google.common.collect.Lists;

public class _Function {
	public static <A> Transducer<A, A> parallel() {
		return new Transducer<A, A>() {
			@Override
			public <R> CompletingReducer<R, A> apply(CompletingReducer<R, ? super A> rf) {
				return new ReducerOn<R, A>(rf) {
					@Override
					public Reduction<R> reduce(R result, A input) {
						return rf.reduce(result, input);
					}
				};
			};
		};
	}

	/**
	* Creates a transducer that transforms a reducing function by applying a
	* predicate to each input and processing only those inputs for which the
	* predicate is true.
	* @param p a predicate function
	* @param <A> input type of input and output reducing functions
	* @return a new transducer
	*/
	public static <A> Transducer<A, A> filter(final Predicate<A> p) {
		return new Transducer<A, A>() {
			@Override
			public <R> CompletingReducer<R, A> apply(CompletingReducer<R, ? super A> rf) {
				return new ReducerOn<R, A>(rf) {
					@Override
					public Reduction<R> reduce(R result, A input) {
						if (p.test(input)) {
							return rf.reduce(result, input);
						}

						return Reduction.incomplete(result);
					}
				};
			};
		};
	}

	/**
	* Creates a transducer that transforms a reducing function such that
	* it only processes n inputs, then the reducing process stops.
	* @param n the number of inputs to process
	* @param <A> input type of input and output reducing functions
	* @return a new transducer
	*/
	public static <A> Transducer<A, A> take(final long numberOfItems, boolean strict) {
		return new Transducer<A, A>() {
			@Override
			public <R> CompletingReducer<R, A> apply(CompletingReducer<R, ? super A> rf) {
				return new ReducerOn<R, A>(rf) {
					long taken = 0;

					@Override
					public Reduction<R> reduce(R result, A input) {
						if (taken < numberOfItems - 1) {
							taken++;
							return rf.reduce(result, input);
						}
						else if (taken < numberOfItems) {
							taken++;
							return Reduction.halt(rf.reduce(result, input));
						}
						else {
							return Reduction.haltIncomplete(result);
						}
					}

					@Override
					public Reduction<R> complete(R result) {
						if (taken != numberOfItems && strict) {
							throw new RuntimeException("Tried to take " + numberOfItems + " elements but only found " + numberOfItems);
						}
						else {
							return rf.complete(result);
						}
					}
				};
			}
		};
	}

	public static <A> Transducer<A, A> first(boolean strict) {
		return take(1, strict);
	}

	public static <A> Transducer<A, A> sort(final Comparator<A> comparator) {
		return new Transducer<A, A>() {
			@Override
			public <R> CompletingReducer<R, A> apply(CompletingReducer<R, ? super A> rf) {
				return new CompletingReducer<R, A>() {
					List<A> inputs = Lists.newArrayList();
					List<A> sortedInputs = null;
					int currentIndex = 0;

					@Override
					public Reduction<R> complete(R result) {
						if (sortedInputs == null) {
							Collections.sort(inputs, comparator);
							sortedInputs = inputs;
						}

						if (currentIndex < sortedInputs.size()) {
							currentIndex++;
							return rf.reduce(result, sortedInputs.get(currentIndex - 1));
						}
						else {
							return rf.complete(result);
						}
					}

					@Override
					public Reduction<R> reduce(R result, A input) {
						inputs.add(input);
						return Reduction.incomplete(result);
					}
				};
			};
		};
	}

	public static <T, N> Transducer<T, N> map(Function<N, T> function) {
		return new Transducer<T, N>() {
			@Override
			public <R> CompletingReducer<R, N> apply(CompletingReducer<R, ? super T> reducer) {
				return new ReducerOn<R, N>(reducer) {
					@Override
					public Reduction<R> reduce(R result, N input) {
						return reducer.reduce(result, function.apply(input));
					}
				};
			}
		};
	}

	public static <A, P> Transducer<Iterable<A>, A> partitionBy(final Function<A, P> f) {
		return new Transducer<Iterable<A>, A>() {
			@Override
			public <R> CompletingReducer<R, A> apply(final CompletingReducer<R, ? super Iterable<A>> rf) {
				return new CompletingReducer<R, A>() {
					List<A> part = new ArrayList<A>();
					Object mark = new Object();
					Object prior = mark;

					@Override
					public Reduction<R> complete(R result) {
						R ret = result;
						if (!part.isEmpty()) {
							List<A> copy = new ArrayList<A>(part);
							part.clear();
							return rf.reduce(result, copy);
						}

						return rf.complete(ret);
					}

					@Override
					public Reduction<R> reduce(R result, A input) {
						P val = f.apply(input);
						if ((prior == mark) || (prior.equals(val))) {
							prior = val;
							part.add(input);
							return Reduction.incomplete(result);
						} else {
							List<A> copy = new ArrayList<A>(part);
							prior = val;
							part.clear();
							part.add(input);
							return rf.reduce(result, copy);
						}
					}
				};
			}
		};
	}

	/**
	* Creates a transducer that transforms a reducing function that processes
	* iterables of input into a reducing function that processes individual inputs
	* by gathering series of inputs into partitions of a given size, only forwarding
	* them to the next reducing function when enough inputs have been accrued. Processes
	* any remaining buffered inputs when the reducing process completes.
	* @param n the size of each partition
	* @param <A> the input type of the input and output reducing functions
	* @return a new transducer
	*/
	public static <A> Transducer<Iterable<A>, A> partition(int n) {
		return new Transducer<Iterable<A>, A>() {
			@Override
			public <R> CompletingReducer<R, A> apply(final CompletingReducer<R, ? super Iterable<A>> rf) {
				return new CompletingReducer<R, A>() {
					List<A> part = new ArrayList<A>(n);

					@Override
					public Reduction<R> complete(R result) {
						R ret = result;
						if (!part.isEmpty()) {
							List<A> copy = new ArrayList<A>(part);
							part.clear();
							return rf.reduce(result, copy);
						}

						return rf.complete(ret);
					}

					@Override
					public Reduction<R> reduce(R result, A input) {
						part.add(input);
						if (n == part.size()) {
							List<A> copy = new ArrayList<A>(part);
							part.clear();
							return rf.reduce(result, copy);
						}

						return Reduction.incomplete(result);
					}
				};
			}
		};
	}

	public static void main(String[] args) {

		Transducer<String, Integer> toString = map((a) -> {
			System.out.println("Mapping " + a);
			return a.toString();
		});
		Transducer<Long, String> parseLong = map((a) -> Long.parseLong(a));
		Transducer<Long, Long> sort = sort((a, b) -> a.compareTo(b));
		Transducer<Iterable<Long>, Long> partition = partition(2);
		Transducer<Long, Long> take = take(3, false);

		Transducer<Iterable<Long>, Integer> combined =
				toString.compose(parseLong).compose(sort).compose(partition);

		Iterator<Iterable<Long>> test2 = combined.transduceDeferred(Lists.newArrayList(3, 2, 1, 6, 23, 3, 3).iterator(), Lists.newArrayList(), (result, input) -> input);
		test2.forEachRemaining((iterable) -> {
			iterable.forEach(System.out::println);
			System.out.println("-");
		});

		System.out.println("=========================");

		System.out.println("=========================");

		Transducer<Iterable<Long>, Integer> combined2 =
				toString.compose(parseLong).compose(take).compose(partition);

		Iterator<Iterable<Long>> test4 = combined2.transduceDeferred(Lists.newArrayList(3, 2, 1, 6, 23, 3, 3).iterator(), Lists.newArrayList(), (result, input) -> input);
		test4.forEachRemaining((iterable) -> {
			iterable.forEach(System.out::println);
			System.out.println("-");
		});
	}
}