package org.alloy.metal.function;

import org.alloy.metal.collections.list.MutableList;
import org.alloy.metal.collections.list._Lists;
import org.alloy.metal.function.Transducer.Reducer;

public interface Flow<T> extends Transduceable<T> {
	public default MutableList<T> collectList() {
		return collect(_Lists.<T> empty(), (result, value) -> {
			result.add(value);
			return result;
		});
	}

	public <R> R collect(R initial, Reducer<R, ? super T> reducer);
}