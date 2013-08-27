package com.mst.graph;

import java.util.Set;


public abstract class AbstractSourceSinkGraph<T> implements ISourceSinkGraph<T, IDirectedEdge<T>>{

	private final T startVertex;
	private final T endVertex;
	private final IDirectedAcyclicGraph<T, IDirectedEdge<T>> aonNetworkGraph;
	
	public AbstractSourceSinkGraph(
			T source, 
			T sink,
			IDirectedAcyclicGraph<T, IDirectedEdge<T>> aonNetworkGraph) {
		this.startVertex = source;
		this.endVertex = sink;
		this.aonNetworkGraph = aonNetworkGraph;
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
		return aonNetworkGraph.getEdges();
	}

	@Override
	public IDirectedEdge<T> getEdge(T source, T target) {
		return aonNetworkGraph.getEdge(source, target);
	}

	@Override
	public Set<T> getSuccessors(T activity) {
		return aonNetworkGraph.getSuccessors(activity);
	}

	@Override
	public Set<T> getPredecessors(T activity) {
		return aonNetworkGraph.getPredecessors(activity);
	}

	@Override
	public Set<T> getVertexSet() {
		return aonNetworkGraph.getVertexSet();
	}
}
