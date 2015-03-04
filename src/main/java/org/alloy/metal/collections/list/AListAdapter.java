package org.alloy.metal.collections.list;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.alloy.metal.collections.size.FixedSizeInfo;
import org.alloy.metal.collections.size.SizeInfo;
import org.alloy.metal.delegation.Delegator;
import org.alloy.metal.iteration._Iteration;
import org.alloy.metal.iteration.cursor.Cursor;

public class AListAdapter<N extends List<T>, T> implements AList<T>, Delegator<N> {
	private N list;

	public AListAdapter(N list) {
		this.list = list;
	}

	@Override
	public N getDelegate() {
		return list;
	}

	@Override
	public SizeInfo getSizeInfo() {
		return new FixedSizeInfo(this.getDelegate().size());
	}

	@Override
	public boolean contains(T element) {
		return getDelegate().contains(element);
	}

	@Override
	public Cursor<T> cursor() {
		return _Iteration.cursor(this.getDelegate().iterator());
	}

	@Override
	public T get(int index) {
		return getDelegate().get(index);
	}

	@Override
	public List<T> asList() {
		return Collections.unmodifiableList(this.getDelegate());
	}

	@Override
	public Collection<T> asCollection() {
		return getDelegate();
	}
}