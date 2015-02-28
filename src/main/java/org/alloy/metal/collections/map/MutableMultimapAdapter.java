package org.alloy.metal.collections.map;

import com.google.common.collect.Multimap;

public class MutableMultimapAdapter<T, N> extends AMultimapAdapter<T, N> implements MutableMultimap<T, N> {
	public MutableMultimapAdapter(Multimap<T, N> multimap) {
		super(multimap);
	}

	@Override
	public boolean put(T key, N value) {
		return this.getDelegate().put(key, value);
	}

	@Override
	public boolean remove(T key) {
		return this.getDelegate().removeAll(key).isEmpty();
	}
}