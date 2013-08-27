package com.mst.graph;

import java.util.Set;

import org.jgrapht.DirectedGraph;
import org.jgrapht.Graphs;

import com.google.common.collect.Sets;

public class DirectedAcyclicGraphJGraphTImpl<T, V extends IDirectedEdge<T>> implements IDirectedAcyclicGraph<T, V > {
	
	private final DirectedGraph<T, V> network;

	public DirectedAcyclicGraphJGraphTImpl(
			DirectedGraph<T, V> network) {
		// TODO check acyclic
		this.network = network;
	}

	protected DirectedGraph<T, V> getNetwork() {
		return network;
	}
	
	@Override
	public Set<V> getEdges() {
		return network.edgeSet();
	}

	@Override
	public V getEdge(T source, T target) {
		V edge = network.getEdge(source, target);
		return edge;
	}

	@Override
	public Set<T> getSuccessors(T vertex) {
		return Sets.newHashSet(Graphs.successorListOf(network, vertex));
	}

	@Override
	public Set<T> getPredecessors(T vertex) {
		return Sets.newHashSet(Graphs.predecessorListOf(network, vertex));
	}

	@Override
	public Set<T> getVertexSet() {
		return network.vertexSet();
	}
	
	@Override
	public Set<V> getIncomingEdgesOf(T vertex) {
		return this.network.incomingEdgesOf(vertex);
	}
	
	@Override
	public Set<V> getOutgoingEdgesOf(T vertex) {
		return this.network.outgoingEdgesOf(vertex);
	}
	
}
