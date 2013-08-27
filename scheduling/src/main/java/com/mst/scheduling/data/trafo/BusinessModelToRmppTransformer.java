package com.mst.scheduling.data.trafo;

import java.math.RoundingMode;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.jgrapht.DirectedGraph;
import org.jgrapht.EdgeFactory;
import org.jgrapht.graph.DefaultDirectedGraph;

import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.google.common.math.DoubleMath;
import com.mst.graph.DirectedAcyclicGraphJGraphTImpl;
import com.mst.graph.IDirectedAcyclicGraph;
import com.mst.scheduling.algorithm.rmpp.ListPriorizationFunction;
import com.mst.scheduling.algorithm.rmpp.RoadmapProjectStage;
import com.mst.scheduling.data.business.BusinessDomainModel;
import com.mst.scheduling.data.business.IEmployee;
import com.mst.scheduling.data.business.IEpic;
import com.mst.scheduling.data.business.IProjectStage;
import com.mst.scheduling.data.business.IProjectStageSkill;
import com.mst.scheduling.data.business.IStory;
import com.mst.scheduling.data.business.IStoryRelation;
import com.mst.scheduling.data.business.ITeam;
import com.mst.scheduling.data.business.IWorkLoad;
import com.mst.scheduling.data.business.SkillLevel;
import com.mst.scheduling.data.rmpp.IMultiResource;
import com.mst.scheduling.data.rmpp.IPriorizationFunction;
import com.mst.scheduling.data.rmpp.IProjectNetwork;
import com.mst.scheduling.data.rmpp.IResourceGroup;
import com.mst.scheduling.data.rmpp.IRoadmapProblem;
import com.mst.scheduling.data.rmpp.IRoadmapProject;
import com.mst.scheduling.data.rmpp.IRoadmapProjectRelation;
import com.mst.scheduling.data.rmpp.IRoadmapProjectStage;
import com.mst.scheduling.data.rmpp.MultiResource;
import com.mst.scheduling.data.rmpp.ProjectNetwork;
import com.mst.scheduling.data.rmpp.ResourceGroup;
import com.mst.scheduling.data.rmpp.RoadmapProblem;
import com.mst.scheduling.data.rmpp.RoadmapProject;
import com.mst.scheduling.data.rmpp.RoadmapProjectRelation;

public class BusinessModelToRmppTransformer {
	
	public static final IRoadmapProblem createRoadmapProblem(BusinessDomainModel businessDomainModel) {
		Set<IResourceGroup> resourceGroups = createResourceGroups(businessDomainModel.getTeams());
		IProjectNetwork projectNetwork = createProjectNetwork(businessDomainModel);
		IPriorizationFunction priorizationFunction = new ListPriorizationFunction();
		IRoadmapProblem instance = new RoadmapProblem(projectNetwork.getVertexSet(), resourceGroups, priorizationFunction, projectNetwork);
		return instance;
	}

	private static IProjectNetwork createProjectNetwork(
			BusinessDomainModel businessDomainModel) {
		EdgeFactory<IRoadmapProject, IRoadmapProjectRelation>  edgeFactory = new ProjectRelationsFactory();
		DirectedGraph<IRoadmapProject, IRoadmapProjectRelation> dag = new DefaultDirectedGraph<IRoadmapProject, IRoadmapProjectRelation>(edgeFactory);
		dag.addVertex(RoadmapProject.START_DUMMY);
		dag.addVertex(RoadmapProject.END_DUMMY);
		BiMap<IStory, IRoadmapProject> storyToProjectMap = createStoryToProjectMap(businessDomainModel);
		for(IRoadmapProject project : storyToProjectMap.values()) {
			dag.addVertex(project);
		}
		
		Set<IRoadmapProjectRelation> relations = createRoadmapProjectRelations(storyToProjectMap, businessDomainModel.getStoryRelations());
		for(IRoadmapProjectRelation relation : relations) {
			dag.addEdge(relation.getSource(), relation.getTarget(), relation);
		}
		
		IDirectedAcyclicGraph<IRoadmapProject, IRoadmapProjectRelation> graph = new DirectedAcyclicGraphJGraphTImpl<IRoadmapProject, IRoadmapProjectRelation>(dag);
		IProjectNetwork network = new ProjectNetwork(RoadmapProject.START_DUMMY, RoadmapProject.END_DUMMY, graph);
		return network;
	}

	private static Set<IRoadmapProjectRelation> createRoadmapProjectRelations(
			BiMap<IStory, IRoadmapProject> storyToProjectMap,
			ImmutableSet<IStoryRelation> storyRelations) {
		Set<IRoadmapProjectRelation> relations = Sets.newHashSet();
		Set<IRoadmapProject> noPrequisitesProjects = Sets.newHashSet(storyToProjectMap.values());
		Set<IRoadmapProject> noSuccessorProjects = Sets.newHashSet(storyToProjectMap.values());
		for(IStoryRelation storyRelation : storyRelations) {
			IRoadmapProject source = storyToProjectMap.get(storyRelation.getPredecessor());
			IRoadmapProject target = storyToProjectMap.get(storyRelation.getSuccessor());
			float prerequisitePercentage = 100f; // TODO: 
			IRoadmapProjectRelation roadmapRelation = new RoadmapProjectRelation(source, target, prerequisitePercentage);
			relations.add(roadmapRelation);
			noPrequisitesProjects.remove(storyRelation.getSuccessor());
			noSuccessorProjects.remove(storyRelation.getPredecessor());
		}
		Collection<? extends IRoadmapProjectRelation> startRelations = createStartRelations(noSuccessorProjects);
		relations.addAll(startRelations);
		Collection<? extends IRoadmapProjectRelation> endRelations = createEndRelations(noPrequisitesProjects);
		relations.addAll(endRelations);
		return relations;
	}

	private static Collection<? extends IRoadmapProjectRelation> createEndRelations(
			Set<IRoadmapProject> noPrequisitesProjects) {
		Set<IRoadmapProjectRelation> startRelations = Sets.newHashSet();
		for(IRoadmapProject story : noPrequisitesProjects) {
			IRoadmapProjectRelation relation = new RoadmapProjectRelation(story, RoadmapProject.END_DUMMY, 100f);
			startRelations.add(relation);
		}
		return startRelations;
	}

	private static Collection<? extends IRoadmapProjectRelation> createStartRelations(
			Set<IRoadmapProject> noSuccessorProjects) {
		Set<IRoadmapProjectRelation> startRelations = Sets.newHashSet();
		for(IRoadmapProject story : noSuccessorProjects) {
			IRoadmapProjectRelation relation = new RoadmapProjectRelation(RoadmapProject.START_DUMMY, story, 100f);
			startRelations.add(relation);
		}
		return startRelations;
	}

	private static BiMap<IStory, IRoadmapProject> createStoryToProjectMap(
			BusinessDomainModel businessDomainModel) {
		BiMap<IStory, IRoadmapProject> storyToProjectMap = HashBiMap.create();
		for(IEpic epic : businessDomainModel.getEpics()) {
			for(IStory story : epic.getStories()) {
				String id = createId(epic, story);
				List<IRoadmapProjectStage> stages = createStages(story.getStageToWorkloadMap());
				IRoadmapProject project = new RoadmapProject(id, stages);
				storyToProjectMap.put(story, project);
			}
		}
		return storyToProjectMap;
	}

	private static List<IRoadmapProjectStage> createStages(
			LinkedHashMap<IProjectStage, Map<IProjectStageSkill, IWorkLoad>> stageToWorkloadMap) {
		List<IRoadmapProjectStage> stages = Lists.newArrayList();
		for(Entry<IProjectStage, Map<IProjectStageSkill, IWorkLoad>> stageEntry : stageToWorkloadMap.entrySet()) {
			String id = stageEntry.getKey().getName();
			Map<IProjectStageSkill, Integer> workDemand = convert(stageEntry.getValue());
			int maxMultiResource = 3;
			IRoadmapProjectStage stage = new RoadmapProjectStage(id, workDemand, maxMultiResource);
			stages.add(stage);
		}
		return stages;
	}

	// TODO is IWorkLoad neccessary?
	private static Map<IProjectStageSkill, Integer> convert(
			Map<IProjectStageSkill, IWorkLoad> workSpec) {
		Map<IProjectStageSkill, Integer> converted = Maps.newHashMap();
		for(Entry<IProjectStageSkill, IWorkLoad> specEntry : workSpec.entrySet()) {
			Integer value = DoubleMath.roundToInt(specEntry.getValue().getEstimation() * 8, RoundingMode.CEILING);
			converted.put(specEntry.getKey(), value);
		}
		return converted;
	}

	private static String createId(IEpic epic, IStory story) {
		return epic.getId() + "/" + story.getId() + " - " + story.getTitle();
	}

	private static Set<IResourceGroup> createResourceGroups(
			ImmutableSet<ITeam> teams) {
		Set<IResourceGroup> resourceGroups = Sets.newHashSet();
		for(ITeam team : teams) {
			IResourceGroup group = createResourceGroup(team);
			resourceGroups.add(group);
		}
		return resourceGroups;
	}

	private static IResourceGroup createResourceGroup(ITeam team) {
		Set<IMultiResource> multiResources = createMultiResources(team.getEmployees());
		IResourceGroup group = new ResourceGroup(multiResources);
		return group;
	}

	private static Set<IMultiResource> createMultiResources(Set<IEmployee> employees) {
		Set<IMultiResource> resources = Sets.newHashSet();
		for(IEmployee employee : employees) {
			IMultiResource reource = createResource(employee);
			resources.add(reource);
		}
		return resources;
	}

	private static IMultiResource createResource(IEmployee employee) {
		Map<IProjectStageSkill, Double> resourceSupplyMap = createResourceSupplyMap(employee.getSkillLevelMap());
		IMultiResource resource = new MultiResource(employee.getName(), resourceSupplyMap);
		return resource;
	}

	private static Map<IProjectStageSkill, Double> createResourceSupplyMap(
			Map<IProjectStageSkill, SkillLevel> skillLevelMap) {
		Map<IProjectStageSkill, Double> resourceSupplyMap = Maps.newHashMap();
		for(Entry<IProjectStageSkill, SkillLevel> skillEntry : skillLevelMap.entrySet()) {
			IProjectStageSkill workType = skillEntry.getKey();
			double supplyFactor = SkillLevel.getSupplyFactor(skillEntry.getValue());
			resourceSupplyMap.put(workType, supplyFactor);
		}
		return resourceSupplyMap;
	}

}
