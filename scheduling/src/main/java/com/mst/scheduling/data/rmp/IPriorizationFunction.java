package com.mst.scheduling.data.rmp;

import java.util.Set;

public interface IPriorizationFunction {

	IRoadmapProject getHighestPriorization(Set<IRoadmapProject> eligibleProjects);

}
