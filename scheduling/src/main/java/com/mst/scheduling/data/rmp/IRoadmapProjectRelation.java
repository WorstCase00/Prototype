package com.mst.scheduling.data.rmp;

import com.mst.graph.IDirectedEdge;

public interface IRoadmapProjectRelation extends IDirectedEdge<IRoadmapProject>{

	float getPrerequisitePercentage();
}
