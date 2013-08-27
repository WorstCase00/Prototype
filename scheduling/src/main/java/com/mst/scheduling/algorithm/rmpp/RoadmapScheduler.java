package com.mst.scheduling.algorithm.rmpp;

import java.math.RoundingMode;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.base.Joiner;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.google.common.math.DoubleMath;
import com.mst.function.DiscreteInterval;
import com.mst.function.IDiscreteInterval;
import com.mst.function.IDiscreteStepFunction;
import com.mst.scheduling.data.business.IProjectStageSkill;
import com.mst.scheduling.data.rmp.IMultiResource;
import com.mst.scheduling.data.rmp.IResourceGroup;
import com.mst.scheduling.data.rmp.IRoadmapProject;
import com.mst.scheduling.data.rmp.IRoadmapProjectStage;
import com.mst.scheduling.data.rmp.IRoadmapSchedule;

public class RoadmapScheduler implements IRoadmapScheduler {

	private static final Logger LOGGER = LoggerFactory.getLogger(RoadmapScheduler.class);
	
	@Override
	public void schedule(
			IRoadmapProject project, 
			IResourceGroup resourceGroup,
			IRoadmapSchedule schedule) {
		LOGGER.debug("schedule project {} with resource group {}", project, resourceGroup);
		List<IRoadmapProjectStage> projectStages = project.getProjectStages();
		int releaseTime = schedule.getReleaseTime(project);
		for(IRoadmapProjectStage stage : projectStages) {
			LOGGER.debug("schedule project stage {} with release time {}", projectStages, releaseTime);
			int stageEnd = -1;
			Map<IMultiResource, Map<IProjectStageSkill, Double>> workAssignments = chooseWorkAssignments(stage, resourceGroup);
			LOGGER.debug("chosen work assignment: {}", Joiner.on(",").join(workAssignments.entrySet()));
			for(Entry<IMultiResource, Map<IProjectStageSkill, Double>> workAssignmentEntry : workAssignments.entrySet()) {
				IMultiResource resource = workAssignmentEntry.getKey();
				Map<IProjectStageSkill, Double> workTypeToDemandMap = workAssignmentEntry.getValue();
				for(Entry<IProjectStageSkill, Double> workDemandEntry : workTypeToDemandMap.entrySet()) {
					Double demand = workDemandEntry.getValue();
					IDiscreteStepFunction freeFunction = schedule.getFreeFunction(resource);
					List<IDiscreteInterval> intervals = findEarliestIDiscreteIntervals(freeFunction, releaseTime, demand);
					for(IDiscreteInterval interval : intervals) {
						schedule.schedule(project, stage, resource, workDemandEntry.getKey(), interval);
					}
					if(intervals.isEmpty()) {
						continue;
					}
					stageEnd = Math.max(stageEnd, intervals.get(intervals.size() - 1).getUpper());
				}
			}
			releaseTime = stageEnd;
		}
		schedule.endProject(project, releaseTime);
	}

	private List<IDiscreteInterval> findEarliestIDiscreteIntervals(
			IDiscreteStepFunction freeFunction,
			int releaseTime, 
			double demand) {
		int intDemand = DoubleMath.roundToInt(demand, RoundingMode.CEILING);
		List<IDiscreteInterval> intervals = Lists.newArrayList();
		while(intDemand > 0) {
			int start = freeFunction.getXWhereYGeq(1, releaseTime);
			int stop = freeFunction.getXWhereYLeq(0, start);
			int available = stop - start;
			if(available > intDemand) {
				stop = start + intDemand;
			}
			IDiscreteInterval interval = new DiscreteInterval(start, stop);
			intervals.add(interval);
			intDemand -= interval.getSize();
		}
		return intervals;
	}

	private Map<IMultiResource, Map<IProjectStageSkill, Double>> chooseWorkAssignments(
			IRoadmapProjectStage stage, IResourceGroup resourceGroup) {

		Map<IProjectStageSkill, Integer> workDemands = stage.getWorkDemand();
		int maxMultiResources = stage.getMaxMultiResources();
		Map<IMultiResource, Map<IProjectStageSkill, Double>> assignments = Maps.newHashMap();
		for(Entry<IProjectStageSkill, Integer> workDemand : workDemands.entrySet()) {
			IProjectStageSkill workType = workDemand.getKey();
			Set<IMultiResource> availableResources = getAvailableResources(resourceGroup.getMultiResources(), workType);
			Set<IMultiResource> selectedResources = select(availableResources, maxMultiResources);
			Map<IMultiResource, Double> workAssignments = calculateWorkAssignments(selectedResources, workDemand);
			for(Entry<IMultiResource, Double> workAssignment : workAssignments.entrySet()) {
				IMultiResource resource = workAssignment.getKey();
				if(!assignments.containsKey(resource)) {
					Map<IProjectStageSkill, Double> empty = Maps.newHashMap();
					assignments.put(resource, empty);
				}
				assignments.get(resource).put(workType, workAssignment.getValue());
			}
		}
		return assignments;
	}

	private Map<IMultiResource, Double> calculateWorkAssignments(
			Set<IMultiResource> selectedResources, Entry<IProjectStageSkill, Integer> workDemand) {
		double availableWorkSum = 0;
		for(IMultiResource resource : selectedResources) {
			IProjectStageSkill workType = workDemand.getKey();
			Double supply = resource.getWorkSupplyMap().get(workType);
			availableWorkSum += supply;
		}
		double processingTime = ((double) workDemand.getValue()) / availableWorkSum;
		Map<IMultiResource, Double> workAssignments = Maps.newHashMap();
		for(IMultiResource resource : selectedResources) {
			workAssignments.put(resource, processingTime);
		}
		return workAssignments;
	}

	private Set<IMultiResource> select(Set<IMultiResource> availableResources,
			int maxMultiResources) {
		Set<IMultiResource> selected = Sets.newHashSet();
		if(availableResources.size() <= maxMultiResources) {
			return availableResources;
		}
		List<IMultiResource> resourceList = Lists.newArrayList(availableResources);
		Collections.shuffle(resourceList);
		List<IMultiResource> subList = resourceList.subList(0, maxMultiResources);
		selected.addAll(subList);
		return selected;
	}

	private Set<IMultiResource> getAvailableResources(
			Set<IMultiResource> multiResources, IProjectStageSkill workType) {
		Set<IMultiResource> availableResource = Sets.newHashSet();
		for(IMultiResource resource : multiResources) {
			Map<IProjectStageSkill, Double> resourceSupplyMap = resource.getWorkSupplyMap();
			if(resourceSupplyMap.containsKey(workType)) {
				availableResource.add(resource);
			}
		}
		return availableResource;
	}

}
