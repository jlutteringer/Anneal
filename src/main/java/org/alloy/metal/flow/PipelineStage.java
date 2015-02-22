package org.alloy.metal.flow;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Spliterator;

import org.alloy.metal.collections.list.AList;
import org.alloy.metal.collections.list._Lists;
import org.alloy.metal.data.DataCharacteristics;
import org.alloy.metal.function.Tuple.Pair;
import org.alloy.metal.function._Tuple;
import org.alloy.metal.iteration.FetchingSpliterator;
import org.alloy.metal.transducer.DelegatingTransductionContext;
import org.alloy.metal.transducer.Transducer;
import org.alloy.metal.transducer.TransductionContext;

import com.google.common.collect.Lists;

public interface PipelineStage<T, N> {
	public Spliterator<T> bind(boolean parallel, Spliterator<N> input);

	public static class IntermediatePipelineStage<T, N, R> implements PipelineStage<T, N> {
		private TransductionContext<T, R> internalTransducer;
		private PipelineStage<R, N> pipelineChain;
		private AList<IntermediatePipelineStage<?, N, ?>> stages = _Lists.list();

		public IntermediatePipelineStage(PipelineStage<R, N> pipelineChain, Transducer<T, R> internalTransducer) {
			this.pipelineChain = pipelineChain;

			if (internalTransducer instanceof TransductionContext) {
				this.internalTransducer = (TransductionContext<T, R>) internalTransducer;
			}
			else {
				this.internalTransducer = new DelegatingTransductionContext<>(internalTransducer);
			}
		}

		public TransductionContext<T, R> getInternalTransducer() {
			return internalTransducer;
		}

		@SuppressWarnings("unchecked")
		@Override
		public Spliterator<T> bind(boolean parallel, Spliterator<N> input) {
			DataCharacteristics currentInputCharacteristics = null;
			List<Pair<DataCharacteristics, List<IntermediatePipelineStage<?, N, ?>>>> partitions = Lists.newArrayList();
			for (IntermediatePipelineStage<?, N, ?> stage : stages) {
				DataCharacteristics nextInputCharacteristics = stage.getInternalTransducer().getDescription().getInputCharacteristics();
				if (!this.areCompatibleCharacteristics(currentInputCharacteristics, nextInputCharacteristics)) {
					currentInputCharacteristics = nextInputCharacteristics;
					partitions.add(_Tuple.of(currentInputCharacteristics, Lists.newArrayList(stage)));
				}
				else {
					partitions.get(partitions.size() - 1).getSecond().add(stage);
				}
			}

			List<Pair<DataCharacteristics, Spliterator<Object>>> fusedSpliterators = Lists.newArrayList();
			for (Pair<DataCharacteristics, List<IntermediatePipelineStage<?, N, ?>>> partition : partitions) {
				Transducer<Object, Object> transducer = null;
				for (IntermediatePipelineStage<?, N, ?> stage : partition.getSecond()) {
					if (transducer == null) {
						transducer = (Transducer<Object, Object>) stage.getInternalTransducer();
					}
					else {
						transducer = transducer.compose((Transducer<Object, Object>) stage.getInternalTransducer());
					}
				}

				Spliterator<Object> object = buildIntermediateSpliterator(partition.getFirst(), transducer);
				fusedSpliterators.add(_Tuple.of(partition.getFirst(), object));
			}

			return null;
		}

		private Spliterator<Object> buildIntermediateSpliterator(DataCharacteristics first, Transducer<Object, Object> transducer) {
			// TODO Auto-generated method stub
			return null;
		}

		private boolean areCompatibleCharacteristics(DataCharacteristics currentInputCharacteristics, DataCharacteristics inputCharacteristics) {
			// TODO Auto-generated method stub
			return false;
		}
	}

	public static class BasePipelineChain<T> implements PipelineStage<T, T> {
		@Override
		public Spliterator<T> bind(boolean parallel, Spliterator<T> input) {
			return input;
		}
	}

	public static class FooSpliterator<T, N> extends FetchingSpliterator<T> {
		private Spliterator<N> input;

		@Override
		public Spliterator<T> trySplit() {
			Spliterator<N> split = input.trySplit();
			if (split != null) {
				return new FooSpliterator<T, N>();
			}
			return null;
		}

		@Override
		public long estimateSize() {
			// TODO Auto-generated method stub
			return 0;
		}

		@Override
		public int characteristics() {
			// TODO Auto-generated method stub
			return 0;
		}

		@Override
		protected T fetch() throws NoSuchElementException {
			// TODO Auto-generated method stub
			return null;
		}
	}
}