package com.mst.scheduling.data.trafo.wpn;

import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.jgrapht.graph.DefaultDirectedGraph;

import com.mst.graph.DirectedAcyclicGraphJGraphTImpl;
import com.mst.graph.GraphUtils;
import com.mst.graph.IDirectedAcyclicGraph;
import com.mst.graph.IDirectedEdge;
import com.mst.scheduling.data.business.IProjectStage;
import com.mst.scheduling.data.business.IProjectStageSkill;
import com.mst.scheduling.data.business.IStory;
import com.mst.scheduling.data.business.IWorkLoad;

class WorkPackageNetworkFromStoryCreator {
	
	static IWorkPackageNetwork createWorkPackageNetwork(IStory story) {
		DefaultDirectedGraph<IWorkPackage, IDirectedEdge<IWorkPackage>> graph = GraphUtils.createDefaultDirectedGraph();
		IWorkPackage source = WorkPackageFactory.createStartNode(story);
		graph.addVertex(source);
		IWorkPackage previous = source;
		Set<Entry<IProjectStage, Map<IProjectStageSkill, IWorkLoad>>> stageWorkEntries = story.getStageToWorkloadMap().entrySet();
		for(Entry<IProjectStage, Map<IProjectStageSkill, IWorkLoad>> stageWorkEntry : stageWorkEntries) {
			IWorkPackageNetwork stageWorkNetwork = WorkPackageNetworkFromStageCreator.createWorkPackageNetworkForStage(stageWorkEntry, story);
			IWorkPackage stageSource = stageWorkNetwork.getSource();
			graph.addEdge(previous, stageSource);
			IWorkPackage stageSink = stageWorkNetwork.getSink();
			previous = stageSink;
		}
		IWorkPackage sink = WorkPackageFactory.createEndNode(story);
		graph.addVertex(sink);
		graph.addEdge(previous, sink);
		
		IDirectedAcyclicGraph<IWorkPackage, IDirectedEdge<IWorkPackage>> networkGraph = new DirectedAcyclicGraphJGraphTImpl<IWorkPackage, IDirectedEdge<IWorkPackage>>(graph);
		IWorkPackageNetwork network = new WorkPackageNetwork(source, sink, networkGraph);
		return network;
	}
}
