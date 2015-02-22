package org.alloy.metal.transducer;

import org.alloy.metal.flow.PipelineStageDescription;

public class DelegatingTransductionContext<T, N> extends TransductionContext<T, N> {
	private Transducer<T, N> delegate;

	public DelegatingTransductionContext(Transducer<T, N> delegate) {
		super(PipelineStageDescription.DEFAULT);
		this.delegate = delegate;
	}

	public DelegatingTransductionContext(Transducer<T, N> delegate, PipelineStageDescription description) {
		super(description);
		this.delegate = delegate;
	}

	@Override
	public <R> CompletingReducer<R, N> apply(CompletingReducer<R, ? super T> reducer) {
		return delegate.apply(reducer);
	}
}
