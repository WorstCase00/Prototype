package com.mst.scheduling.data.mdtrtp;

import java.util.Set;

import com.mst.graph.IDirectedEdge;
import com.mst.graph.ISourceSinkGraph;

public interface IMdtrtpInstance extends IRenewableResourceAvailability{

	int getActivityCount();
	
	ISourceSinkGraph<IActivity, IDirectedEdge<IActivity>> getAonNetwork();
	
	Set<IActivity> getPredecessorsOf(IActivity activity);
	
	Set<IActivity> getSuccessorsOf(IActivity activity);
}
