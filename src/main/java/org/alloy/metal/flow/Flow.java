package org.alloy.metal.flow;

import org.alloy.metal.collections.list.MutableList;
import org.alloy.metal.collections.list._Lists;
import org.alloy.metal.transducer.Transduceable;
import org.alloy.metal.transducer.Transducer.Reducer;

public interface Flow<T> extends Transduceable<T> {
	public default MutableList<T> collectList() {
		return collect(_Lists.<T> empty(), (result, value) -> {
			result.add(value);
			return result;
		});
	}

	public default Flow<T> parallel() {
		throw new UnsupportedOperationException();
	}

	public default Flow<T> sequential() {
		return this;
	}

	public <R> R collect(R initial, Reducer<R, ? super T> reducer);
}