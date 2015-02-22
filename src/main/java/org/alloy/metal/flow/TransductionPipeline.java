package org.alloy.metal.flow;

import java.util.Iterator;
import java.util.Spliterator;

import org.alloy.metal.flow.PipelineStage.BasePipelineChain;
import org.alloy.metal.flow.PipelineStage.IntermediatePipelineStage;
import org.alloy.metal.transducer.Transducer;
import org.alloy.metal.transducer.Transducer.Reducer;

public class TransductionPipeline<T, N> implements Flow<T> {
	private boolean parallel = false;
	private Iterator<N> iterator;

	private PipelineStage<T, N> pipelineStages;

	public TransductionPipeline(Iterator<N> iterator, PipelineStage<T, N> pipelineStages) {
		this.pipelineStages = pipelineStages;
		this.iterator = iterator;
	}

	@Override
	public Flow<T> parallel() {
		parallel = true;
		return this;
	}

	@Override
	public Flow<T> sequential() {
		parallel = false;
		return this;
	}

	@Override
	public <R> Flow<R> compose(Transducer<R, T> transducer) {
		return new TransductionPipeline<R, N>(iterator, new IntermediatePipelineStage<>(pipelineStages, transducer));
	}

	@Override
	public Iterator<T> iterator() {
		return null;
//		return pipelineStages.fuse(parallel).transduceDeferred(iterator);
	}

	@Override
	public Spliterator<T> spliterator() {
		return null; // pipelineStages.bind(parallel, iterator);
	}

	@Override
	public <R> R collect(R initial, Reducer<R, ? super T> reducer) {
		return null;
//		return internalTransducer.transduce(iterator, initial, reducer);
	}

	public static class BaseTransductionPipeline<T> extends TransductionPipeline<T, T> {
		public BaseTransductionPipeline(Iterator<T> iterator) {
			super(iterator, new BasePipelineChain<>());
		}
	}
}