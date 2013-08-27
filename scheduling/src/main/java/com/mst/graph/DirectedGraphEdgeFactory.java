package com.mst.graph;

import org.jgrapht.EdgeFactory;


public class DirectedGraphEdgeFactory<T> implements EdgeFactory<T, IDirectedEdge<T>> {
	
	@Override
	public IDirectedEdge<T> createEdge(T sourceVertex,
			T targetVertex) {
		IDirectedEdge<T> edge = new DirectedGraphEdge<T>(sourceVertex, targetVertex);
		return edge;
	}

}
