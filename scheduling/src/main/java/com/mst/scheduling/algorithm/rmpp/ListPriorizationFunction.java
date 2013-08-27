package com.mst.scheduling.algorithm.rmpp;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Set;

import com.google.common.collect.Lists;
import com.mst.scheduling.data.rmpp.IPriorizationFunction;
import com.mst.scheduling.data.rmpp.IRoadmapProject;

public class ListPriorizationFunction implements IPriorizationFunction {

	@Override
	public IRoadmapProject getHighestPriorization(
			Set<IRoadmapProject> eligibleProjects) {
		List<IRoadmapProject> list = Lists.newArrayList(eligibleProjects);
		Collections.sort(list, new Comparator<IRoadmapProject>() {

			@Override
			public int compare(IRoadmapProject p1, IRoadmapProject p2) {
				return p1.getId().compareTo(p2.getId());
			}
		});
		return list.get(0);
	}

}
