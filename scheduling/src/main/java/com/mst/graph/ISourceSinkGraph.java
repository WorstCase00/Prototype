package com.mst.graph;

public interface ISourceSinkGraph<T, V extends IDirectedEdge<T>> extends IDirectedAcyclicGraph<T, V> {

	T getSource();
	
	T getSink();
}
