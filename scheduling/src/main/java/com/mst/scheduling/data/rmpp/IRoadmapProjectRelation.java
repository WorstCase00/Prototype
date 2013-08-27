package com.mst.scheduling.data.rmpp;

import com.mst.graph.IDirectedEdge;

public interface IRoadmapProjectRelation extends IDirectedEdge<IRoadmapProject>{

	float getPrerequisitePercentage();
}
