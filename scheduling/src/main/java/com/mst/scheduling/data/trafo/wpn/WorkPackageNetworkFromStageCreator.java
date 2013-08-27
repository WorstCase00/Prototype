package com.mst.scheduling.data.trafo.wpn;

import java.util.Map;
import java.util.Map.Entry;

import org.jgrapht.graph.DefaultDirectedGraph;

import com.mst.graph.DirectedAcyclicGraphJGraphTImpl;
import com.mst.graph.GraphUtils;
import com.mst.graph.IDirectedAcyclicGraph;
import com.mst.graph.IDirectedEdge;
import com.mst.scheduling.data.business.IProjectStage;
import com.mst.scheduling.data.business.IProjectStageSkill;
import com.mst.scheduling.data.business.IStory;
import com.mst.scheduling.data.business.IWorkLoad;

class WorkPackageNetworkFromStageCreator {
	
	static IWorkPackageNetwork createWorkPackageNetworkForStage(
			Entry<IProjectStage, Map<IProjectStageSkill, IWorkLoad>> stageWorkEntry, IStory story) {
		IProjectStage stage = stageWorkEntry.getKey();
		DefaultDirectedGraph<IWorkPackage, IDirectedEdge<IWorkPackage>> graph = GraphUtils.createDefaultDirectedGraph();
		IWorkPackage source = WorkPackageFactory.createStartNode(stage, story);
		graph.addVertex(source);
		IWorkPackage sink = WorkPackageFactory.createEndNode(stage, story);
		graph.addVertex(sink);
		Map<IProjectStageSkill, IWorkLoad> skillToWorkMap = stageWorkEntry.getValue();
		for(Entry<IProjectStageSkill, IWorkLoad> skillToWorkEntry : skillToWorkMap.entrySet()) {
			IWorkPackage skillPackage = WorkPackageFactory.createPackage(story, skillToWorkEntry);
			graph.addVertex(skillPackage);
		}
		IDirectedAcyclicGraph<IWorkPackage, IDirectedEdge<IWorkPackage>> networkGraph = new DirectedAcyclicGraphJGraphTImpl<IWorkPackage, IDirectedEdge<IWorkPackage>>(graph);
		IWorkPackageNetwork network = new WorkPackageNetwork(source, sink, networkGraph);
		return network;
	}
}
