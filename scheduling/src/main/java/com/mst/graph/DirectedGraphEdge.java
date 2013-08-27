package com.mst.graph;


public class DirectedGraphEdge<T> implements IDirectedEdge<T> {

	private final T source;
	private final T target;

	public DirectedGraphEdge(T source, T target) {
		this.source = source;
		this.target = target;
	}

	@Override
	public T getSource() {
		return source;
	}

	@Override
	public T getDepending() {
		return target;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((source == null) ? 0 : source.hashCode());
		result = prime * result + ((target == null) ? 0 : target.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		DirectedGraphEdge other = (DirectedGraphEdge) obj;
		if (source == null) {
			if (other.source != null)
				return false;
		} else if (!source.equals(other.source))
			return false;
		if (target == null) {
			if (other.target != null)
				return false;
		} else if (!target.equals(other.target))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "[source=" + source + ", target=" + target + "]";
	}
	
	
	
}
