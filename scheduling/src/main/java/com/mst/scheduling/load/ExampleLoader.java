package com.mst.scheduling.load;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.AbstractMap;
import java.util.Collection;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.SortedMap;
import java.util.StringTokenizer;

import org.apache.commons.io.IOUtils;
import org.joda.time.PeriodType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.base.CharMatcher;
import com.google.common.base.Joiner;
import com.google.common.base.Splitter;
import com.google.common.base.Strings;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.mst.scheduling.algorithm.rmpp.RmppConstructionAlgorithm;
import com.mst.scheduling.data.business.BusinessDomainModel;
import com.mst.scheduling.data.business.Employee;
import com.mst.scheduling.data.business.Epic;
import com.mst.scheduling.data.business.IEmployee;
import com.mst.scheduling.data.business.IEpic;
import com.mst.scheduling.data.business.IProjectStage;
import com.mst.scheduling.data.business.IProjectStageSkill;
import com.mst.scheduling.data.business.IStory;
import com.mst.scheduling.data.business.IStoryRelation;
import com.mst.scheduling.data.business.ITeam;
import com.mst.scheduling.data.business.IWorkLoad;
import com.mst.scheduling.data.business.PredecessorRelation;
import com.mst.scheduling.data.business.ProjectStage;
import com.mst.scheduling.data.business.ProjectStageSkill;
import com.mst.scheduling.data.business.SkillLevel;
import com.mst.scheduling.data.business.Story;
import com.mst.scheduling.data.business.Team;
import com.mst.scheduling.data.business.WorkLoad;
import com.mst.scheduling.data.rmpp.IRoadmapProblem;
import com.mst.scheduling.data.rmpp.IRoadmapSchedule;
import com.mst.scheduling.data.trafo.BusinessModelToRmppTransformer;

public class ExampleLoader {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(ExampleLoader.class);
	private static final String STAGE_SKILL_SPEC_PATH = "src/main/resources/ProjectStageSkillSpecification.txt";
	private static final String TEAMS_EMPLOYEES_SPEC_PATH = "src/main/resources/TeamPersonalSpecification.txt";
	private static final int NAME_COL_INDEX = 1;
	private static final int HOURS_COL_INDEX = 2;
	private static final int FIRST_SKILL_COL_INDEX = 6;
	private static final String EPICS_SPEC_PATH = "src/main/resources/EpicsSpecification.txt";
	private static final int EPIC_ID_COL_INDEX = 0;
	private static final int EPIC_TITLE_COL_INDEX = 1;
	private static final int STORY_ID_COL_INDEX = 3;
	private static final int STORY_TITLE_COL_INDEX = 4;
	private static final PeriodType DEFAULT_WORKLOAD_TIMEUNIT = PeriodType.days();
	private static final int STORY_PREDS_COL_INDEX = 5;
	
	public static void main(String[] args) throws IOException {
		FileInputStream inputStream = new FileInputStream(STAGE_SKILL_SPEC_PATH);
	    try {
	    	List<String> projectStageAndSkillsString = IOUtils.readLines(inputStream);
	    	String skillString = projectStageAndSkillsString.get(1);
			List<IProjectStageSkill> skills = Collections.unmodifiableList(extractProjectStageSkills(skillString));
//			System.out.println(Joiner.on(",").join(skills));
			
			String stageString = projectStageAndSkillsString.get(0);
			List<IProjectStage> stages = extractProjectStages(stageString, skills);
//			System.out.println(Joiner.on(",").join(stages));
			
			
			List<String> teamsEmployeesStrings = IOUtils.readLines(new FileInputStream(TEAMS_EMPLOYEES_SPEC_PATH));
			List<ITeam> teams = extractTeamsList(teamsEmployeesStrings, skills);
//	    	System.out.println(Joiner.on(",").join(teams));
	    	
	    	List<String> epicsStrings = IOUtils.readLines(new FileInputStream(EPICS_SPEC_PATH));
			Map<IEpic, Map<IStory, Set<String>>> epicsToStoryPredsMap = extractEpicsToStoryPredsMap(epicsStrings, stages, skills);
	    	Set<IEpic> epics = epicsToStoryPredsMap.keySet();
//			System.out.println(Joiner.on(",").join(epics));
	    	
	    	Set<IStoryRelation> storyRelations = extractStoryRelations(epicsToStoryPredsMap.values(), epics) ;
			BusinessDomainModel domainModel = new BusinessDomainModel(Sets.newHashSet(teams), stages, epics, storyRelations);
			
			IRoadmapProblem problem = BusinessModelToRmppTransformer.createRoadmapProblem(domainModel);
			RmppConstructionAlgorithm algo = new RmppConstructionAlgorithm();
			IRoadmapSchedule construct = algo.construct(problem);
			System.out.println(construct);
			System.out.println("makespan: " + construct.getMakeSpan());
	    } finally {
	        inputStream.close();
	    }
	}

	private static Set<IStoryRelation> extractStoryRelations(
			Collection<Map<IStory, Set<String>>> values, Set<IEpic> epics) {
		Set<IStoryRelation> storyRelations = Sets.newHashSet();
		Map<IStory, Set<String>> storyToPredStringMap = mergeMaps(values);
		for(Entry<IStory, Set<String>> predecessorEntry : storyToPredStringMap.entrySet()) {
			IStory story = predecessorEntry.getKey();
			for(String predCode : predecessorEntry.getValue()) {
				if(predCode.isEmpty()) {
					continue;
				}
				Set<IStory> predecessors = getPredecessorsForId(predCode, storyToPredStringMap.keySet(), epics);
				for(IStory predecessor : predecessors) {
					IStoryRelation relation = new PredecessorRelation(predecessor, story);
					storyRelations.add(relation);
				}
			}
		}
		return storyRelations;
	}

	private static Set<IStory> getPredecessorsForId(String predCode, Set<IStory> stories, Set<IEpic> epics) {
		for(IEpic epic : epics) {
			if(epic.getId().equals(predCode)) {
				return epic.getStories();
			}
		}
		for(IStory story : stories) {
			if(story.getId().equals(predCode)) {
				return Sets.newHashSet(story);
			}
		}
		throw new RuntimeException();
	}

	private static <K, V> Map<K, V> mergeMaps(
			Collection<Map<K, V>> maps) {
		Map<K, V> merged = Maps.newHashMap();
		for(Map<K, V> map : maps) {
			merged.putAll(map);
		}
		return merged;
	}

	private static Map<IEpic, Map<IStory, Set<String>>> extractEpicsToStoryPredsMap(
			List<String> epicsStrings, 
			List<IProjectStage> stages, 
			List<IProjectStageSkill> skillList) {
		Map<IEpic, Map<IStory, Set<String>>> epicsToStoryMap = Maps.newHashMap();
		Map<IStory, Set<String>> storyToPredIdMap = Maps.newHashMap();
		String epicTitle = null;
		String epicId = null;
		for(String storyString : epicsStrings) {
			if(Strings.isNullOrEmpty(storyString.trim())) {
				continue;
			}
			LOGGER.debug("create story for string: {}", storyString);
			Iterable<String> strings = Splitter.on('\t').split(storyString);
			List<String> words = Lists.newArrayList(strings);
			if(!words.get(0).isEmpty()) {
				if(epicTitle != null) {
					IEpic epic = new Epic(epicId, epicTitle, storyToPredIdMap.keySet());
					epicsToStoryMap.put(epic, storyToPredIdMap);
				}
				storyToPredIdMap = Maps.newHashMap();
				epicId = words.get(EPIC_ID_COL_INDEX);
				epicTitle = words.get(EPIC_TITLE_COL_INDEX);
			}
			Entry<IStory, Set<String>> storyToPredIdsEntry = extractStoryAndPredecessorIds(words, stages, skillList);
			storyToPredIdMap.put(storyToPredIdsEntry.getKey(), storyToPredIdsEntry.getValue());
		}
		// TODO last epic is missing
		return epicsToStoryMap;
	}

	private static Entry<IStory, Set<String>> extractStoryAndPredecessorIds(
			List<String> words,
			List<IProjectStage> stages, 
			List<IProjectStageSkill> skills) {
		String id = words.get(STORY_ID_COL_INDEX);
		String title = words.get(STORY_TITLE_COL_INDEX);
		CharSequence predString = words.get(STORY_PREDS_COL_INDEX);
		Iterable<String> predecessors = Splitter.on(", ").split(predString);
		Set<String> predecessorCodes = Sets.newHashSet(predecessors);
		LinkedHashMap<IProjectStage, Map<IProjectStageSkill, IWorkLoad>> stageToWorkLoadMap = extractSkillToWorkLoadMapList(
				words, 
				stages,
				skills);
		IStory story = new Story(
				id, 
				title, 
				stageToWorkLoadMap);
		LOGGER.debug("created story {} with predecessor strings {}", story, Joiner.on(",").join(predecessorCodes));
		return new AbstractMap.SimpleEntry<IStory, Set<String>>(story, predecessorCodes);
	}

	private static LinkedHashMap<IProjectStage, Map<IProjectStageSkill, IWorkLoad>> extractSkillToWorkLoadMapList(
			List<String> words, 
			List<IProjectStage> stages,
			List<IProjectStageSkill> skillsList) {
		LinkedHashMap<IProjectStage, Map<IProjectStageSkill, IWorkLoad>> stageToWorkLoadMap = Maps.newLinkedHashMap();
		LinkedHashMap<IProjectStage, List<String>> estimationStringMap = extractEstimationStringMap(words, stages);
		Map<IProjectStage, List<IProjectStageSkill>> stageToSkillMap = extractStageToSkillMap(stages, skillsList);
		for(Entry<IProjectStage, List<String>> estimationStringEntry : estimationStringMap.entrySet()) {
			Map<IProjectStageSkill, IWorkLoad> workLoadMap = Maps.newHashMap();
			IProjectStage stage = estimationStringEntry.getKey();
			List<String> estimationStrings = estimationStringEntry.getValue();
			List<IProjectStageSkill> stageSkillList = stageToSkillMap.get(stage);
			for(int i = 0; i < stageSkillList.size(); i++) {
				String estimationString = estimationStrings.get(i);
				IProjectStageSkill skill = stageSkillList.get(i);
				if(estimationString == null || estimationString.trim().isEmpty()) {
					continue;
				}
				Float estimation = Float.parseFloat(estimationString.replace(',', '.'));
				IWorkLoad load = new WorkLoad(estimation, DEFAULT_WORKLOAD_TIMEUNIT);
				workLoadMap.put(skill, load);
			}
			stageToWorkLoadMap.put(stage, workLoadMap);
		}
		return stageToWorkLoadMap;
	}

	private static Map<IProjectStage, List<IProjectStageSkill>> extractStageToSkillMap(
			List<IProjectStage> stages, List<IProjectStageSkill> skillsList) {
		Map<IProjectStage, List<IProjectStageSkill>> stageToSkillListMap = Maps.newHashMap();
		LinkedList<IProjectStageSkill> skillListCopy = Lists.newLinkedList(skillsList);
		for(IProjectStage stage : stages) {
			Set<IProjectStageSkill> skillSet = stage.getProjectStageSkills();
			List<IProjectStageSkill> entryList = Lists.newLinkedList();
			while(skillSet.contains(skillListCopy.peekFirst())) {
				entryList.add(skillListCopy.pollFirst());
			}
			stageToSkillListMap.put(stage, entryList);
		}
		return stageToSkillListMap;
	}

	private static LinkedHashMap<IProjectStage, List<String>> extractEstimationStringMap(
			List<String> words, List<IProjectStage> stages) {
		LinkedHashMap<IProjectStage, List<String>> estimationStringMap = Maps.newLinkedHashMap();
		LinkedList<String> wordsCopy = Lists.newLinkedList(words);
		for(int i = 1; i <= stages.size(); i++) {
			IProjectStage stage = stages.get(stages.size() - i);
			int stageSkillCount = stage.getProjectStageSkills().size();
			int estimationCount = stageSkillCount + 1;
			LinkedList<String> entryList = Lists.newLinkedList();
			for(int j = 0; j < estimationCount; j++) {
				String word = wordsCopy.pollLast();
				entryList.addFirst(word);
			}
			estimationStringMap.put(stage, entryList);
			wordsCopy.pollLast();
		}
		return estimationStringMap;
	}

	private static List<ITeam> extractTeamsList(
			List<String> teamsEmployeesStrings, 
			List<IProjectStageSkill> skills) {
		List<ITeam> teams = Lists.newArrayList();
		Set<IEmployee> employees = Sets.newHashSet();
		String teamName = null;
		for(String employeeString : teamsEmployeesStrings) {
			if(Strings.isNullOrEmpty(employeeString.trim())) {
				continue;
			}
			LOGGER.debug("create employee for string: {}", employeeString);
			Iterable<String> strings = Splitter.on('\t').split(employeeString);
			List<String> words = Lists.newArrayList(strings);
			if(!words.get(0).isEmpty()) {
				if(teamName != null) {
					ITeam team = new Team(teamName, employees);
					teams.add(team);
				}
				employees = Sets.newHashSet();
				teamName = words.get(0);
			}
			IEmployee employee = extractEmployee(words, skills);
			employees.add(employee);
		}
		return teams;
	}

	private static IEmployee extractEmployee(
			List<String> words,
			List<IProjectStageSkill> skills) {
		String name = words.get(NAME_COL_INDEX);
		int hoursPerWeek = Integer.parseInt(words.get(HOURS_COL_INDEX));
		Map<IProjectStageSkill, SkillLevel> skillMap = extractSkillMap(words, skills);
		IEmployee employee = new Employee(name, hoursPerWeek, skillMap);
		LOGGER.debug("created employee: {}", employee);
		return employee;
	}

	private static Map<IProjectStageSkill, SkillLevel> extractSkillMap(
			List<String> words, List<IProjectStageSkill> skills) {
		Map<IProjectStageSkill, SkillLevel> skillsMap = Maps.newHashMap();
		List<String> skillEvaluations = words.subList(FIRST_SKILL_COL_INDEX, words.size());
		for(int i = 0; i < skills.size(); i++) {
			String evaluation = skillEvaluations.get(i);
			if(Strings.isNullOrEmpty(evaluation)) {
				continue;
			}
			IProjectStageSkill skill = skills.get(i);
			SkillLevel level = SkillLevel.parse(evaluation);
			skillsMap.put(skill, level);
			
		}
		return skillsMap;
	}

	private static List<IProjectStage> extractProjectStages(
			String stageString,
			List<IProjectStageSkill> skills) {
		LinkedHashMap<String, Integer> stageNameToSkillCountMap = createStageNameToSkillCountMap(stageString);
		List<IProjectStage> stages = createStages(stageNameToSkillCountMap, skills);
		return stages;
	}

	private static List<IProjectStage> createStages(
			LinkedHashMap<String, Integer> stageNameToSkillCountMap,
			List<IProjectStageSkill> skills) {
		LOGGER.debug("create project stages for stageNameToSkillCountMap {} and skill list {}",
				Joiner.on(",").join(stageNameToSkillCountMap.entrySet()),
				Joiner.on(",").join(skills));
		List<IProjectStage> stages = Lists.newArrayList();

		List<IProjectStageSkill> copy = Lists.newArrayList(skills);
		for(Entry<String, Integer> entry : stageNameToSkillCountMap.entrySet()) {
			String name = entry.getKey();
			int skillCount = entry.getValue();
			Set<IProjectStageSkill> stageSkills = getAndRemoveNextSkills(copy, skillCount);
			IProjectStage stage = new ProjectStage(stageSkills, name);
			stages.add(stage);
		}
		LOGGER.debug("created project stages: {}", Joiner.on(",").join(stages));
		return stages;
	}

	private static Set<IProjectStageSkill> getAndRemoveNextSkills(
			List<IProjectStageSkill> skills, int skillCount) {
		Set<IProjectStageSkill> nextSkills = Sets.newHashSet();
		for(int i = 0; i < skillCount; i++) {
			IProjectStageSkill skill = skills.remove(0);
			nextSkills.add(skill);
		}
		return nextSkills;
	}

	private static LinkedHashMap<String, Integer> createStageNameToSkillCountMap(
			String stageString) {
		LOGGER.debug("create stage to skill count map for string {}", stageString);
		List<String> stageNames = extractStageNames(stageString);
		List<String> stageNamesAndTabs = extractStageNamesWithTabs(stageNames, stageString);
		LinkedHashMap<String, Integer> nameToCountMap = Maps.newLinkedHashMap();
		for(int i = 0; i < stageNames.size(); i++) {
			String stageName = stageNames.get(i);
			String stageAndTabs = stageNamesAndTabs.get(i);
			int count = CharMatcher.is('\t').countIn(stageAndTabs) - 1; 
			nameToCountMap.put(stageName, count);
		}
		return nameToCountMap;
	}
	private static List<String> extractStageNamesWithTabs(
			List<String> stageNames, 
			String stageString) {
		List<String> namesWithTabs = Lists.newArrayList();
		for(int i = 0; i < stageNames.size() - 1; i++) {
			String thisStage = stageNames.get(i);
			String nextStage = stageNames.get(i + 1);
			int indexThisStage = stageString.indexOf(thisStage);
			int indexNextStage = stageString.indexOf(nextStage);
			String string = stageString.substring(indexThisStage, indexNextStage);
			namesWithTabs.add(string);
		}
		int lastStageIndex = stageString.indexOf(stageNames.get(stageNames.size() - 1));
		String last = stageString.substring(lastStageIndex);
		namesWithTabs.add(last);
		return namesWithTabs;
	}

	private static List<String> extractStageNames(String stageString) {
		List<String> stageNames = Lists.newArrayList();
		StringTokenizer tokenizer = new StringTokenizer(stageString, "\t");
		while(tokenizer.hasMoreElements()) {
			String next = tokenizer.nextToken();
			stageNames.add(next);
		}
		return stageNames;
	}

	private static List<IProjectStageSkill> extractProjectStageSkills(
			String skillString) {
		StringTokenizer tokenizer = new StringTokenizer(skillString, "\t");
		List<IProjectStageSkill> skills = Lists.newArrayList();
		while(tokenizer.hasMoreElements()) {
			String name = tokenizer.nextToken();
			IProjectStageSkill skill = new ProjectStageSkill(name);
			skills.add(skill);
		}
		return skills;
	}
}
