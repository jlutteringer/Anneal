package org.alloy.metal.collections.map;

import org.alloy.metal.collections.ACollection;

public interface AMultimap<T, N> extends Mapper<T, ACollection<N>>, MapOperations<T, N> {

}