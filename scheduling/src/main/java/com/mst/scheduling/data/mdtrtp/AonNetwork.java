package com.mst.scheduling.data.mdtrtp;

import com.mst.graph.AbstractSourceSinkGraph;
import com.mst.graph.IDirectedAcyclicGraph;
import com.mst.graph.IDirectedEdge;

public class AonNetwork extends AbstractSourceSinkGraph<IActivity> {
	
	public AonNetwork(
			IActivity source, 
			IActivity sink,
			IDirectedAcyclicGraph<IActivity, IDirectedEdge<IActivity>> aonNetworkGraph) {
		super(source, sink, aonNetworkGraph);
	}
}
