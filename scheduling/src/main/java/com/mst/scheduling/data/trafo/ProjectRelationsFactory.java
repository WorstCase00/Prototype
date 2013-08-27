package com.mst.scheduling.data.trafo;

import org.jgrapht.EdgeFactory;

import com.mst.scheduling.data.rmpp.IRoadmapProject;
import com.mst.scheduling.data.rmpp.IRoadmapProjectRelation;
import com.mst.scheduling.data.rmpp.RoadmapProjectRelation;

// TODO: needed?
public class ProjectRelationsFactory implements
		EdgeFactory<IRoadmapProject, IRoadmapProjectRelation> {

	@Override
	public IRoadmapProjectRelation createEdge(
			IRoadmapProject source,
			IRoadmapProject target) {
		return new RoadmapProjectRelation(source, target, 100);
	}

}
