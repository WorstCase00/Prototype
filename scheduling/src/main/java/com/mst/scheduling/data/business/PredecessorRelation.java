package com.mst.scheduling.data.business;

import static com.google.common.base.Preconditions.*;


public class PredecessorRelation implements IStoryRelation {

	private final IStory predecessor;
	private final IStory successor;

	public PredecessorRelation(IStory predecessor, IStory successor) {
		checkNotNull(predecessor);
		checkNotNull(successor);
		this.predecessor = predecessor;
		this.successor = successor;
	}

	@Override
	public IStory getPredecessor() {
		return this.predecessor;
	}

	@Override
	public IStory getSuccessor() {
		return this.successor;
	}

}
