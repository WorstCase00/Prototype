package com.mst.scheduling.data.trafo.wpn;

import java.util.Map;
import java.util.Set;

import org.jgrapht.graph.DefaultDirectedGraph;

import com.google.common.collect.Maps;
import com.mst.graph.DirectedAcyclicGraphJGraphTImpl;
import com.mst.graph.GraphUtils;
import com.mst.graph.IDirectedAcyclicGraph;
import com.mst.graph.IDirectedEdge;
import com.mst.scheduling.data.business.IEpic;
import com.mst.scheduling.data.business.IStory;
import com.mst.scheduling.data.business.IStoryRelation;

public class WorkPackageNetworkCreator {

	public static IWorkPackageNetwork createWorkPackageNetwork(Set<IEpic> epics, Set<IStoryRelation> storyRelations) {
		
		DefaultDirectedGraph<IWorkPackage, IDirectedEdge<IWorkPackage>> graph = GraphUtils.createDefaultDirectedGraph();
		IWorkPackage source = WorkPackageFactory.createStartPackage();
		IWorkPackage sink = WorkPackageFactory.createEndPackage();

		Map<IStory, IWorkPackageNetwork> storyToWorkPackageNetworkMap = createStoryToNetworkMap(epics);
		for(IStoryRelation storyRelation : storyRelations) {
			IStory predecessor = storyRelation.getPredecessor();
			IStory successor = storyRelation.getSuccessor();
			
			IWorkPackageNetwork predecessorNetwork = storyToWorkPackageNetworkMap.get(predecessor);
			IWorkPackageNetwork successorNetwork = storyToWorkPackageNetworkMap.get(successor);
			graph.addEdge(predecessorNetwork.getSink(), successorNetwork.getSource());
		}
		IDirectedAcyclicGraph<IWorkPackage, IDirectedEdge<IWorkPackage>> networkGraph = new DirectedAcyclicGraphJGraphTImpl<IWorkPackage, IDirectedEdge<IWorkPackage>>(graph);
		IWorkPackageNetwork network = new WorkPackageNetwork(source, sink, networkGraph);
		return network;
	}

	private static Map<IStory, IWorkPackageNetwork> createStoryToNetworkMap(
			Set<IEpic> epics) {
		Map<IStory, IWorkPackageNetwork> storyToWorkPackageNetworkMap = Maps.newHashMap();
		for(IEpic epic : epics) {
			Set<IStory> stories = epic.getStories();
			for(IStory story : stories) {
				IWorkPackageNetwork storyNetwork = WorkPackageNetworkFromStoryCreator.createWorkPackageNetwork(story);
				storyToWorkPackageNetworkMap.put(story, storyNetwork);
			}
		}
		return storyToWorkPackageNetworkMap;
	}
}
