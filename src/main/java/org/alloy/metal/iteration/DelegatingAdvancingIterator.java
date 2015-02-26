package org.alloy.metal.iteration;

import java.util.function.Consumer;

import org.alloy.metal.domain.Advancer;

public class DelegatingAdvancingIterator<T> extends AdvancingIterator<T> {
	private Advancer<T> advancer;

	public DelegatingAdvancingIterator(Advancer<T> advancer) {
		this.advancer = advancer;
	}

	@Override
	public boolean tryAdvance(Consumer<? super T> action) {
		return advancer.tryAdvance(action);
	}
}