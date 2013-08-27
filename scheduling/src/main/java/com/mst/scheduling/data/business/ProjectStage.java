package com.mst.scheduling.data.business;

import java.util.Set;

import com.google.common.base.Joiner;
import com.google.common.collect.ImmutableSet;

public class ProjectStage implements IProjectStage {

	private final ImmutableSet<IProjectStageSkill> skills;
	private final String name;

	public ProjectStage(Set<IProjectStageSkill> skills, String name) {
		this.skills = ImmutableSet.copyOf(skills);
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public ImmutableSet<IProjectStageSkill> getProjectStageSkills() {
		return skills;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
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
		ProjectStage other = (ProjectStage) obj;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "ProjectStage [name=" + name + ", skills=" + Joiner.on(",").join(skills) + "]";
	}
	
	

}
