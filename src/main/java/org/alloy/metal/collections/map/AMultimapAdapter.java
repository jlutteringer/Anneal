package org.alloy.metal.collections.map;

import java.util.Collection;
import java.util.Iterator;
import java.util.Map.Entry;

import org.alloy.metal.collections.ACollection;
import org.alloy.metal.collections.SizeInfo;
import org.alloy.metal.collections._Collections;
import org.alloy.metal.delegation.Delegator;

import com.google.common.collect.Multimap;

public class AMultimapAdapter<T, N> implements AMultimap<T, N>, Delegator<Multimap<T, N>> {
	private Multimap<T, N> multimap;

	public AMultimapAdapter(Multimap<T, N> multimap) {
		this.multimap = multimap;
	}

	@Override
	public SizeInfo getSizeInfo() {
		// TODO Auto-generated method stub
		return null;
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
	public Iterator<Entry<T, N>> iterator() {
		return this.getDelegate().entries().iterator();
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