package com.mst.scheduling.algorithm.rmpp;

import com.mst.scheduling.data.rmpp.IResourceGroup;
import com.mst.scheduling.data.rmpp.IRoadmapProject;
import com.mst.scheduling.data.rmpp.IRoadmapSchedule;

public interface IRoadmapScheduler {

	void schedule(IRoadmapProject project, IResourceGroup resourceGroup, IRoadmapSchedule schedule);

}
