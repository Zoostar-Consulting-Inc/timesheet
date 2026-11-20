package com.zoostarinc.timesheet.app.config;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.zoostarinc.timesheet.core.model.WorkflowableTimesheet;
import com.zoostarinc.timesheet.workflow.impl.GenericTimesheetAction;
import com.zoostarinc.timesheet.workflow.impl.GenericTimesheetState;

import lombok.extern.slf4j.Slf4j;
import net.zoostar.common.workflow.Action;
import net.zoostar.common.workflow.State;

@Slf4j
@Configuration
public class WorkflowConfig {
	
	private static final String CREATE_STATE_LOG = "Created bean state: {}";
	private static final String CREATE_ACTION_LOG = "Created bean action: {}";

	@Bean(name = "stateDraft")
	State<WorkflowableTimesheet> stateDraft(Map<String, State<WorkflowableTimesheet>> states) {
		var state = new GenericTimesheetState("Draft", HashSet.newHashSet(2));
		log.debug(CREATE_STATE_LOG, state);
		states.put(state.getName().toUpperCase(), state);
		return state;
	}
	
	@Bean
	Action<WorkflowableTimesheet> actionSave(State<WorkflowableTimesheet> stateDraft) {
		var action = new GenericTimesheetAction("Save", stateDraft);
		stateDraft.getActions().add(action);
		log.debug(CREATE_ACTION_LOG, action);
		return action;
	}
	
	@Bean
	Action<WorkflowableTimesheet> actionSubmit(State<WorkflowableTimesheet> stateSubmitted, State<WorkflowableTimesheet> stateDraft) {
		var action = new GenericTimesheetAction("Submit", stateSubmitted);
		stateDraft.getActions().add(action);
		log.debug(CREATE_ACTION_LOG, action);
		return action;
	}

	@Bean(name = "stateSubmitted")
	State<WorkflowableTimesheet> stateSubmitted(Map<String, State<WorkflowableTimesheet>> states) {
		var state = new GenericTimesheetState("Submitted", HashSet.newHashSet(2));
		log.debug(CREATE_STATE_LOG, state);
		states.put(state.getName().toUpperCase(), state);
		return state;
	}
	
	@Bean
	Action<WorkflowableTimesheet> actionReject(State<WorkflowableTimesheet> stateDraft, State<WorkflowableTimesheet> stateSubmitted) {
		var action = new GenericTimesheetAction("Reject", stateDraft);
		stateSubmitted.getActions().add(action);
		log.debug(CREATE_ACTION_LOG, action);
		return action;
	}
	
	@Bean
	Map<String, State<WorkflowableTimesheet>> states() {
		return new HashMap<>();
	}
	
}
