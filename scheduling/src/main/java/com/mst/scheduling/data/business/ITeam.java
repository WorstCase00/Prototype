package com.mst.scheduling.data.business;

import java.util.Set;

public interface ITeam {

	String getName();
	
	Set<IEmployee> getEmployees();
	
}
