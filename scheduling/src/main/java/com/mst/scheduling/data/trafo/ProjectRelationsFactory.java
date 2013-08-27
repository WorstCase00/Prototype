package com.mst.scheduling.data.trafo;

import org.jgrapht.EdgeFactory;

import com.mst.scheduling.data.rmp.IRoadmapProject;
import com.mst.scheduling.data.rmp.IRoadmapProjectRelation;
import com.mst.scheduling.data.rmp.RoadmapProjectRelation;

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
