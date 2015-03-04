package org.alloy.metal.collections.map;

import java.util.Collection;
import java.util.Collections;
import java.util.Map;
import java.util.Map.Entry;

import org.alloy.metal.collections.ACollection;
import org.alloy.metal.collections._Collections;
import org.alloy.metal.collections.size.FixedSizeInfo;
import org.alloy.metal.collections.size.SizeInfo;
import org.alloy.metal.delegation.Delegator;
import org.alloy.metal.iteration._Iteration;
import org.alloy.metal.iteration.cursor.Cursor;

public class AMapAdapter<T, N> implements AMap<T, N>, Delegator<Map<T, N>> {
	private Map<T, N> map;

	public AMapAdapter(Map<T, N> map) {
		this.map = map;
	}

	@Override
	public Map<T, N> getDelegate() {
		return map;
	}

	@Override
	public SizeInfo getSizeInfo() {
		return new FixedSizeInfo(this.getDelegate().size());
	}

	@Override
	public boolean contains(Entry<T, N> element) {
		return this.getDelegate().entrySet().contains(element);
	}

	@Override
	public Cursor<Entry<T, N>> cursor() {
		return _Iteration.cursor(this.getDelegate().entrySet().iterator());
	}

	@Override
	public boolean containsKey(T key) {
		return this.getDelegate().containsKey(key);
	}

	@Override
	public boolean containsValue(N value) {
		return this.getDelegate().containsValue(value);
	}

	@Override
	public ACollection<N> values() {
		return _Collections.wrap(this.getDelegate().values());
	}

	@Override
	public N get(T key) {
		return this.getDelegate().get(key);
	}

	@Override
	public Map<T, N> asMap() {
		return Collections.unmodifiableMap(this.getDelegate());
	}

	@Override
	public Collection<Entry<T, N>> asCollection() {
		return getDelegate().entrySet();
	}
}