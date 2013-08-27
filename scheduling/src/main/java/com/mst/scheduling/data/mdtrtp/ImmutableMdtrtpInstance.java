package com.mst.scheduling.data.mdtrtp;

import java.util.Set;

public class ImmutableMdtrtpInstance implements IMdtrtpInstance {

	private final IAonNetwork aonNetwork;
	private final IRenewableResourceAvailability resourceAvailability;

	public ImmutableMdtrtpInstance(
			IAonNetwork aonNetwork, 
			IRenewableResourceAvailability resourceAvailability) {
		this.aonNetwork = aonNetwork;
		this.resourceAvailability = resourceAvailability;
	}

	@Override
	public int getActivityCount() {
		return this.aonNetwork.getVertexSet().size();
	}

	@Override
	public IAonNetwork getAonNetwork() {
		return this.aonNetwork;
	}

	@Override
	public Set<IActivity> getPredecessorsOf(IActivity activity) {
		return this.aonNetwork.getPredecessors(activity);
	}

	@Override
	public Set<IActivity> getSuccessorsOf(IActivity activity) {
		return this.aonNetwork.getSuccessors(activity);
	}


	@Override
	public Set<IRenewableResource> getRenewableResources() {
		// TODO Auto-generated method stub
		return null;
	}

}
