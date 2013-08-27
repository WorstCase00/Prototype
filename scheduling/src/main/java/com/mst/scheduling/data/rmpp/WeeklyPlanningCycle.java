package com.mst.scheduling.data.rmpp;

import static com.google.common.base.Preconditions.checkArgument;

import java.util.Collections;
import java.util.List;

import com.google.common.collect.Lists;

public class WeeklyPlanningCycle implements IPlanningCycleDefinition {

	private static final int WORK_HOURS_PER_WEEK = 37;
	private final int weeks;
	private final List<Integer> cycleStarts;

	public WeeklyPlanningCycle(int weeks) {
		checkArgument(weeks > 0, "number of weeks must be strictly positive");
		this.weeks = weeks;
		this.cycleStarts = Lists.newArrayList(0);
	}

	@Override
	public int getNextCycleStart(int endTime) {
		checkArgument(endTime >= 0, "time must be >= 0");
		int latestCycleStart = cycleStarts.get(cycleStarts.size() - 1);
		while(latestCycleStart <= endTime) {
			latestCycleStart += (WORK_HOURS_PER_WEEK * weeks);
			cycleStarts.add(latestCycleStart);
		}
		int index = Collections.binarySearch(cycleStarts, endTime);
		if(index >= 0) {
			return cycleStarts.get(index + 1);
		} 
		return cycleStarts.get(-index -1);
	}

}
