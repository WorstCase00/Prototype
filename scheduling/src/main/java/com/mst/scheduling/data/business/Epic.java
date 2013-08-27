package com.mst.scheduling.data.business;
import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;

import java.util.Set;

import com.google.common.base.Joiner;
import com.google.common.collect.ImmutableSet;

public class Epic implements IEpic {

	private final String id;
	private final String title;
	private final ImmutableSet<IStory> stories;
	
	public Epic(String id, String title, Set<IStory> stories) {
		checkNotNull(id);
		checkNotNull(title);
		checkArgument(!stories.isEmpty());
		this.id = id;
		this.title = title;
		this.stories = ImmutableSet.copyOf(stories);
	}

	public String getId() {
		return this.id;
	}

	public String getTitle() {
		return this.title;
	}

	public Set<IStory> getStories() {
		return this.stories;
	}

	@Override
	public String toString() {
		return "Epic [id=" + id + ", title=" + title + ", stories=" + Joiner.on(",").join(stories)
				+ "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Epic other = (Epic) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
	
	

}
