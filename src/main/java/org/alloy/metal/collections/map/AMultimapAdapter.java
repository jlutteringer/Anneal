package org.alloy.metal.collections.map;

import java.util.Collection;
import java.util.Map.Entry;

import org.alloy.metal.collections.ACollection;
import org.alloy.metal.collections._Collections;
import org.alloy.metal.collections.size.FixedSizeInfo;
import org.alloy.metal.collections.size.SizeInfo;
import org.alloy.metal.delegation.Delegator;
import org.alloy.metal.iteration._Iteration;
import org.alloy.metal.iteration.cursor.Cursor;

import com.google.common.collect.Multimap;

public class AMultimapAdapter<T, N> implements AMultimap<T, N>, Delegator<Multimap<T, N>> {
	private Multimap<T, N> multimap;

	public AMultimapAdapter(Multimap<T, N> multimap) {
		this.multimap = multimap;
	}

	@Override
	public SizeInfo getSizeInfo() {
		return new FixedSizeInfo(this.getDelegate().size());
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
	public ACollection<N> get(T key) {
		return _Collections.wrap(this.getDelegate().get(key));
	}

	@Override
	public ACollection<N> values() {
		return _Collections.wrap(this.getDelegate().values());
	}

	@Override
	public boolean contains(Entry<T, N> element) {
		return this.getDelegate().entries().contains(element);
	}

	@Override
	public Cursor<Entry<T, N>> cursor() {
		return _Iteration.cursor(this.getDelegate().entries().iterator());
	}

	@Override
	public Multimap<T, N> getDelegate() {
		return multimap;
	}

	@Override
	public Collection<Entry<T, N>> asCollection() {
		return getDelegate().entries();
	}
}