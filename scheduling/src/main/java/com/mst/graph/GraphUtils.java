package com.mst.graph;

import org.jgrapht.graph.DefaultDirectedGraph;

public abstract class GraphUtils {
 
	private GraphUtils() {}
	
	public static <T> DefaultDirectedGraph<T, IDirectedEdge<T>> createDefaultDirectedGraph() {
		DirectedGraphEdgeFactory<T> factory = new DirectedGraphEdgeFactory<T>();
		DefaultDirectedGraph<T, IDirectedEdge<T>> network = new DefaultDirectedGraph<T, IDirectedEdge<T>>(factory );
		return network;
	}
}
