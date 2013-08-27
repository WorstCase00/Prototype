package com.mst.scheduling.data.rmpp;

import java.util.Set;

import com.mst.graph.AbstractSourceSinkGraph;
import com.mst.graph.IDirectedAcyclicGraph;
import com.mst.graph.ISourceSinkGraph;

public class ProjectNetwork implements IProjectNetwork {
	
	private IRoadmapProject source;
	private IRoadmapProject sink;
	IDirectedAcyclicGraph<IRoadmapProject, IRoadmapProjectRelation> graph;
	
	public ProjectNetwork(IRoadmapProject source, IRoadmapProject sink, IDirectedAcyclicGraph<IRoadmapProject, IRoadmapProjectRelation> graph) {
		this.source = source;
		this.sink = sink;
		this.graph = graph;
	}

	@Override
	public IRoadmapProject getSource() {
		return this.source;
	}

	@Override
	public IRoadmapProject getSink() {
		return this.sink;
	}

	@Override
	public Set<IRoadmapProjectRelation> getEdges() {
		return this.graph.getEdges();
	}

	@Override
	public IRoadmapProjectRelation getEdge(
			IRoadmapProject source,
			IRoadmapProject target) {
		return this.graph.getEdge(source, target);
	}

	@Override
	public Set<IRoadmapProject> getSuccessors(IRoadmapProject project) {
		return this.graph.getSuccessors(project);
	}

	@Override
	public Set<IRoadmapProject> getPredecessors(IRoadmapProject project) {
		return this.graph.getPredecessors(project);
	}

	@Override
	public Set<IRoadmapProject> getVertexSet() {
		return this.graph.getVertexSet();
	}

}
