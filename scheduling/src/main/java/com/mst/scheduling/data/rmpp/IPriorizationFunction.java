package com.mst.scheduling.data.rmpp;

import java.util.Set;

public interface IPriorizationFunction {

	IRoadmapProject getHighestPriorization(Set<IRoadmapProject> eligibleProjects);

}
