package com.mst.graph;

import java.util.Set;


public abstract class AbstractSourceSinkGraph<T> implements ISourceSinkGraph<T, IDirectedEdge<T>>{

	private final T startVertex;
	private final T endVertex;
	private final IDirectedAcyclicGraph<T, IDirectedEdge<T>> graph;
	
	public AbstractSourceSinkGraph(
			T source, 
			T sink,
			IDirectedAcyclicGraph<T, IDirectedEdge<T>> graph) {
		this.startVertex = source;
		this.endVertex = sink;
		this.graph = graph;
	}

	@Override
	public T getSource() {
		return this.startVertex;
	}

	@Override
	public T getSink() {
		return this.endVertex;
	}

	@Override
	public Set<IDirectedEdge<T>> getEdges() {
		return graph.getEdges();
	}

	@Override
	public IDirectedEdge<T> getEdge(T source, T target) {
		return graph.getEdge(source, target);
	}

	@Override
	public Set<T> getSuccessors(T activity) {
		return graph.getSuccessors(activity);
	}

	@Override
	public Set<T> getPredecessors(T activity) {
		return graph.getPredecessors(activity);
	}

	@Override
	public Set<T> getVertexSet() {
		return graph.getVertexSet();
	}

	@Override
	public Set<IDirectedEdge<T>> getIncomingEdgesOf(T vertex) {
		return this.graph.getIncomingEdgesOf(vertex);
	}

	@Override
	public Set<IDirectedEdge<T>> getOutgoingEdgesOf(T vertex) {
		return graph.getOutgoingEdgesOf(vertex);
	}
}
