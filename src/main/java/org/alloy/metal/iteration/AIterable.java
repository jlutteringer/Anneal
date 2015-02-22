package org.alloy.metal.iteration;

import org.alloy.metal.flow.Flow;
import org.alloy.metal.flow.TransductionPipeline.BaseTransductionPipeline;

public interface AIterable<T> extends Iterable<T> {
	public default Flow<T> flow() {
		return new BaseTransductionPipeline<T>(this.iterator());
	}
}