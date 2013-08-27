package com.mst.scheduling.data.rmpp;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Set;

import com.google.common.collect.Lists;

public class ByIdPriorizationFunction implements IPriorizationFunction {

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
