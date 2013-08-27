package com.mst.scheduling.algorithm.rmpp;

import com.mst.scheduling.data.rmp.IResourceGroup;
import com.mst.scheduling.data.rmp.IRoadmapProject;
import com.mst.scheduling.data.rmp.IRoadmapSchedule;

public interface IRoadmapScheduler {

	void schedule(IRoadmapProject project, IResourceGroup resourceGroup, IRoadmapSchedule schedule);

}
