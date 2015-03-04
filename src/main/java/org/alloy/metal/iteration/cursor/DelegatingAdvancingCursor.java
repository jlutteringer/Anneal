package org.alloy.metal.iteration.cursor;

import java.util.function.Consumer;

import org.alloy.metal.domain.Advancer;

public class DelegatingAdvancingCursor<T> extends AdvancingCursor<T> {
	private Advancer<T> advancer;

	public DelegatingAdvancingCursor(Advancer<T> advancer) {
		this.advancer = advancer;
	}

	@Override
	public boolean tryAdvance(Consumer<? super T> action) {
		return advancer.tryAdvance(action);
	}
}
