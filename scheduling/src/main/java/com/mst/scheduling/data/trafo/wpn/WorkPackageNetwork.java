package com.mst.scheduling.data.trafo.wpn;

import com.mst.graph.AbstractSourceSinkGraph;
import com.mst.graph.IDirectedAcyclicGraph;
import com.mst.graph.IDirectedEdge;

public class WorkPackageNetwork extends AbstractSourceSinkGraph<IWorkPackage> implements IWorkPackageNetwork
{

	public WorkPackageNetwork(
			IWorkPackage source,
			IWorkPackage sink,
			IDirectedAcyclicGraph<IWorkPackage, IDirectedEdge<IWorkPackage>> aonNetworkGraph) {
		super(source, sink, aonNetworkGraph);
	}

}
