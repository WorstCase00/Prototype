package com.mst.scheduling.data.business;

import java.util.Set;

public interface IEpic {
	
	String getId();
	
	String getTitle();
	
	Set<IStory> getStories();
}
