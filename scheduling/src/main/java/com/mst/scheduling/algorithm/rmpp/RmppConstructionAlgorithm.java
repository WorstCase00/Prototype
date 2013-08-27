package com.mst.scheduling.algorithm.rmpp;

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
import com.mst.scheduling.data.business.IProjectStageSkill;
import com.mst.scheduling.data.rmp.IPriorizationFunction;
import com.mst.scheduling.data.rmp.IResourceGroup;
import com.mst.scheduling.data.rmp.IRoadmapProblem;
import com.mst.scheduling.data.rmp.IRoadmapProject;
import com.mst.scheduling.data.rmp.IRoadmapSchedule;
import com.mst.scheduling.data.rmp.RoadMapSchedule;

public class RmppConstructionAlgorithm {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(RmppConstructionAlgorithm.class);

	public IRoadmapSchedule construct(IRoadmapProblem problem) {
		LOGGER.debug("construct roadmap schedule for problem: {}", problem);
		Set<IResourceGroup> resourceGroups = problem.getResourceGroups();
//		Set<IRoadmapProjectGroup> roadmapProjectGroups = problem.getRoadmapProjectGroups();
		Set<IRoadmapProject> roadmapProjects = problem.getRoadmapProjects();
		IPriorizationFunction priorizationFunction = problem.getPriorizationFunction();
		IRoadmapSchedule schedule = new RoadMapSchedule(problem);
		IRoadmapScheduler scheduler = new RoadmapScheduler();
		
		Map<IRoadmapProject, Set<IRoadmapProject>> openPredecessorMap = initializeOpenPredecessorMap(problem);
		Set<IRoadmapProject> done = Sets.newHashSet();
		for (int i = 0; i < roadmapProjects.size(); i++) {
			LOGGER.debug("construction iteration {}/{}", i, roadmapProjects.size());
			LOGGER.debug("todo {} projects: {}", roadmapProjects.size() - done.size(), Sets.difference(roadmapProjects, done));
			Set<IRoadmapProject> eligibleProjects = getEligibleProjects(openPredecessorMap);
			eligibleProjects.removeAll(done);
			LOGGER.debug("found {} eligible projects: {}", eligibleProjects.size(), Joiner.on(",").join(eligibleProjects));
			IRoadmapProject nextProject = priorizationFunction.getHighestPriorization(eligibleProjects);
			IResourceGroup resourceGroup = chooseResourceGroup(nextProject, resourceGroups, schedule);
			scheduler.schedule(nextProject, resourceGroup, schedule);
			openPredecessorMap = updateOpenPredecessorMap(openPredecessorMap, nextProject);
			done.add(nextProject);
			LOGGER.debug("finished {} projects: {}", done.size(), Joiner.on(",").join(done));
		}
		return schedule;
	}

	private Set<IRoadmapProject> getEligibleProjects(
			Map<IRoadmapProject, Set<IRoadmapProject>> openPredecessorMap) {
		Set<IRoadmapProject> eligible = Sets.newHashSet();
		for(Entry<IRoadmapProject, Set<IRoadmapProject>> entry : openPredecessorMap.entrySet()) {
			if(entry.getValue().isEmpty()) {
				eligible.add(entry.getKey());
			}
		}
		return eligible;
	}

	private IResourceGroup chooseResourceGroup(
			IRoadmapProject project,
			Set<IResourceGroup> resourceGroups, 
			IRoadmapSchedule schedule) {
		Set<IResourceGroup> eligibleResourceGroup = getEligibleResourceGroups(resourceGroups, project);
		List<IResourceGroup> groupsList = Lists.newArrayList(eligibleResourceGroup);
		Collections.shuffle(groupsList);
		return groupsList.get(0);
	}

	private Set<IResourceGroup> getEligibleResourceGroups(
			Set<IResourceGroup> resourceGroups, IRoadmapProject project) {
		LOGGER.debug("get eligible resource groups for project: {}", project.getId());
		Set<IResourceGroup> eligibleResourceGroups = Sets.newHashSet();
		Map<IProjectStageSkill, Integer> workDemands = project.getWorkTypeDemands();
		for(IResourceGroup resourceGroup : resourceGroups) {
			Map<IProjectStageSkill, Double> workTypeSupply = resourceGroup.getWorkTypeSupply();
			Set<IProjectStageSkill> availableSkills = workTypeSupply.keySet();
			Set<IProjectStageSkill> neededSkills = workDemands.keySet();
			if(availableSkills.containsAll(neededSkills)) {
				eligibleResourceGroups.add(resourceGroup);
			} else {
				Set<IProjectStageSkill> missingSkills = Sets.difference(neededSkills, availableSkills);
				System.out.println("missing skill count " + missingSkills.size());
				LOGGER.debug("missing skill or resource group {}: {}", resourceGroup, Joiner.on(",").join(missingSkills));
			}
		}
		LOGGER.debug("found eligible resource groups {}", Joiner.on(",").join(eligibleResourceGroups));
		return eligibleResourceGroups;
	}

	private Map<IRoadmapProject, Set<IRoadmapProject>> updateOpenPredecessorMap(
			Map<IRoadmapProject, Set<IRoadmapProject>> openPredecessorMap,
			IRoadmapProject toRemove) {
		for(Set<IRoadmapProject> openPreds : openPredecessorMap.values()) {
			openPreds.remove(toRemove);
		}
		return openPredecessorMap;
	}

	private Map<IRoadmapProject, Set<IRoadmapProject>> initializeOpenPredecessorMap(
			IRoadmapProblem problem) {
		Map<IRoadmapProject, Set<IRoadmapProject>> openPredecessorMap = Maps.newHashMap();
		for(IRoadmapProject project : problem.getRoadmapProjects()) {
			Set<IRoadmapProject> predecessors = problem.getPrerequisites(project);
			openPredecessorMap.put(project, predecessors);
		}
		return openPredecessorMap;
	}
}
