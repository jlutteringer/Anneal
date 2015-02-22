package org.alloy.metal.transducer;

import org.alloy.metal.flow.PipelineStageDescription;

public abstract class TransductionContext<T, N> implements Transducer<T, N> {
	private PipelineStageDescription description;

	public TransductionContext() {
		this(PipelineStageDescription.DEFAULT);
	}

	public TransductionContext(PipelineStageDescription description) {
		this.description = description;
	}

	public PipelineStageDescription getDescription() {
		return description;
	}
}