package com.mst.graph;

import java.util.Set;

import org.jgrapht.DirectedGraph;
import org.jgrapht.Graphs;

import com.google.common.collect.Sets;
import com.mst.scheduling.data.mdtrtp.IActivity;

public class DirectedAcyclicGraphJGraphTImpl<T, V extends IDirectedEdge<T>> implements IDirectedAcyclicGraph<T, V > {
	
	private final DirectedGraph<T, V> network;

	public DirectedAcyclicGraphJGraphTImpl(
			DirectedGraph<T, V> network) {
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
	public Set<T> getSuccessors(T activity) {
		return Sets.newHashSet(Graphs.successorListOf(network, activity));
	}

	@Override
	public Set<T> getPredecessors(T activity) {
		return Sets.newHashSet(Graphs.predecessorListOf(network, activity));
	}

	@Override
	public Set<T> getVertexSet() {
		return network.vertexSet();
	}

	public static IDirectedAcyclicGraph<IActivity, IDirectedEdge<IActivity>> wrap(
			DirectedGraph<IActivity, IDirectedEdge<IActivity>> graph) {
		// TODO: check if acyclic
		return new DirectedAcyclicGraphJGraphTImpl<IActivity, IDirectedEdge<IActivity>>(graph);
	}
	
}
