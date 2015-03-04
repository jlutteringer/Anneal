package org.alloy.metal.collections.set;

import java.util.Collection;
import java.util.Set;

import org.alloy.metal.collections.size.FixedSizeInfo;
import org.alloy.metal.collections.size.SizeInfo;
import org.alloy.metal.delegation.Delegator;
import org.alloy.metal.iteration._Iteration;
import org.alloy.metal.iteration.cursor.Cursor;

public class MutableSetAdapter<T> implements MutableSet<T>, Delegator<Set<T>> {
	private Set<T> set;

	public MutableSetAdapter(Set<T> set) {
		this.set = set;
	}

	@Override
	public Set<T> getDelegate() {
		return set;
	}

	@Override
	public SizeInfo getSizeInfo() {
		return new FixedSizeInfo(this.getDelegate().size());
	}

	@Override
	public boolean contains(T element) {
		return this.getDelegate().contains(element);
	}

	@Override
	public Collection<T> asCollection() {
		return this.getDelegate();
	}

	@Override
	public Cursor<T> cursor() {
		return _Iteration.cursor(this.getDelegate());
	}

	@Override
	public boolean add(T element) {
		return this.getDelegate().add(element);
	}

	@Override
	public boolean remove(T element) {
		return this.getDelegate().remove(element);
	}
}