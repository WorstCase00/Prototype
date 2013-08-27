package com.mst.scheduling.data.trafo;

import java.util.Map;

import com.mst.scheduling.data.business.IProjectStageSkill;
import com.mst.scheduling.data.business.IWorkLoad;

public interface IProjectStageTask {

	Map<IProjectStageSkill, IWorkLoad> getWorkMap();
}
