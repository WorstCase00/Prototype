package com.mst.scheduling.data.rmpp;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import com.google.common.base.Joiner;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.mst.function.DiscreteStepFunction;
import com.mst.function.IDiscreteInterval;
import com.mst.function.IDiscreteStepFunction;
import com.mst.scheduling.data.business.IProjectStageSkill;
import com.mst.scheduling.data.trafo.wpn.WorkAssignment;

public class RoadMapSchedule implements IRoadmapSchedule {

	private final IRoadmapProblem problem;
	
	// state
	private final Map<IMultiResource, Set<WorkAssignment>> workAssignmentMap;
	private final Map<IMultiResource, IDiscreteStepFunction> freeFunctionMap;
	private final Map<IRoadmapProject, Integer> releaseTimesMap;
	private final Map<IRoadmapProject, Integer> endTimsMap;
	
	public RoadMapSchedule(IRoadmapProblem problem) {
		this.problem = problem;
		this.freeFunctionMap = initFreeFunctionMap(problem.getResourceGroups());
		this.releaseTimesMap = initReleaseTimesMap(problem.getRoadmapProjects());
		this.endTimsMap = initEndTimesMap(problem.getRoadmapProjects());
		this.workAssignmentMap = initWorkAssignmentMap(problem.getResourceGroups());
	}

	private Map<IRoadmapProject, Integer> initEndTimesMap(
			Set<IRoadmapProject> roadmapProjects) {
		Map<IRoadmapProject, Integer> releaseMap = Maps.newHashMap();
		for(IRoadmapProject project : roadmapProjects) {
			releaseMap.put(project, null);
		}
		return releaseMap;
	}

	private Map<IMultiResource, Set<WorkAssignment>> initWorkAssignmentMap(
			Set<IResourceGroup> resourceGroups) {
		Map<IMultiResource, Set<WorkAssignment>> assignmentMap = Maps.newHashMap();
		for(IResourceGroup resourceGroup : resourceGroups) {
			for(IMultiResource resource : resourceGroup.getMultiResources()) {
				Set<WorkAssignment> empty = Sets.newHashSet();
				assignmentMap.put(resource, empty);
			}
		}
		return assignmentMap;
	}

	private Map<IRoadmapProject, Integer> initReleaseTimesMap(
			Set<IRoadmapProject> roadmapProjects) {
		Map<IRoadmapProject, Integer> releaseMap = Maps.newHashMap();
		for(IRoadmapProject project : roadmapProjects) {
			releaseMap.put(project, 0);
		}
		return releaseMap;
	}

	private Map<IMultiResource, IDiscreteStepFunction> initFreeFunctionMap(
			Set<IResourceGroup> resourceGroups) {
		Map<IMultiResource, IDiscreteStepFunction> functionMap = Maps.newHashMap();
		for(IResourceGroup resourceGroup : resourceGroups) {
			for(IMultiResource resource : resourceGroup.getMultiResources()) {
				IDiscreteStepFunction function = new DiscreteStepFunction(0, 1);
				functionMap.put(resource, function);
			}
		}
		return functionMap;
	}

	@Override
	public int getReleaseTime(IRoadmapProject project) {
		return releaseTimesMap.get(project);
	}

	@Override
	public IDiscreteStepFunction getFreeFunction(IMultiResource resource) {
		return this.freeFunctionMap.get(resource);
	}


	@Override
	public void schedule(
			IRoadmapProject project, 
			IRoadmapProjectStage stage,
			IMultiResource resource, 
			IProjectStageSkill skill, 
			IDiscreteInterval interval) {
		IDiscreteStepFunction freeFunction = this.freeFunctionMap.get(resource);
		freeFunction.add(interval, -1);
		WorkAssignment workAssignment = new WorkAssignment(project, stage, skill, interval);
		workAssignmentMap.get(resource).add(workAssignment);
	}

	@Override
	public void endProject(IRoadmapProject project, int endTime) {
		this.endTimsMap.put(project, endTime);
		Set<IRoadmapProject> dependents = problem.getDependents(project);
		for(IRoadmapProject dependent : dependents) {
			Integer oldRelease = this.releaseTimesMap.get(dependent);
			this.releaseTimesMap.put(dependent, Math.max(oldRelease, endTime));
		}
	}

	@Override
	public String toString() {
		return Joiner.on(",").join(this.endTimsMap.entrySet());
	}

	@Override
	public int getMakeSpan() {
		List<Entry<IRoadmapProject, Integer>> endTimeEntries = Lists.newArrayList(this.endTimsMap.entrySet());
		Comparator<Entry<IRoadmapProject, Integer>> comparator = new Comparator<Map.Entry<IRoadmapProject,Integer>>() {

			@Override
			public int compare(Entry<IRoadmapProject, Integer> o1,
					Entry<IRoadmapProject, Integer> o2) {
				return o2.getValue().compareTo(o1.getValue());
			}
		};
		Collections.sort(endTimeEntries, comparator);
		return endTimeEntries.get(0).getValue();
	}
	
	

}
