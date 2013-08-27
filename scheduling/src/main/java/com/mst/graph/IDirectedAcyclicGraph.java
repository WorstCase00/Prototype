package com.mst.graph;

import java.util.Set;

public interface IDirectedAcyclicGraph<T, V extends IDirectedEdge<T>> {

	Set<V> getEdges();

	V getEdge(T source, T target);

	Set<T> getSuccessors(T activity);
	
	Set<T> getPredecessors(T activity);

	Set<T> getVertexSet();

	Set<V> getIncomingEdgesOf(T vertex);

	Set<V> getOutgoingEdgesOf(T vertex);
}