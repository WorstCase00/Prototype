package com.mst.scheduling.data.business;

public interface IStoryRelation {

	IStory getPredecessor();
	
	IStory getSuccessor();
}
