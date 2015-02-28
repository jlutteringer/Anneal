package org.alloy.metal.collections.map;

import java.util.Map;

public class MutableMapAdapter<T, N> extends AMapAdapter<T, N> implements MutableMap<T, N> {
	public MutableMapAdapter(Map<T, N> map) {
		super(map);
	}

	@Override
	public boolean put(T key, N value) {
		return this.getDelegate().put(key, value) != null;
	}

	@Override
	public boolean remove(T key) {
		return this.getDelegate().remove(key) != null;
	}

	@Override
	public Map<T, N> asMap() {
		return this.getDelegate();
	}
}