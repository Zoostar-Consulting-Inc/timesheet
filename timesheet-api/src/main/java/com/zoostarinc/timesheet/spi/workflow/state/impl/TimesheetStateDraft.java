package com.zoostarinc.timesheet.spi.workflow.state.impl;

import java.util.Collection;
import java.util.HashSet;

import org.springframework.stereotype.Component;

import com.zoostarinc.timesheet.core.model.WorkflowableTimesheet;
import com.zoostarinc.timesheet.spi.workflow.action.impl.TimesheetActionSave;

import net.zoostar.common.workflow.Action;

@Component
public class TimesheetStateDraft extends AbstractTimeheetState {

	public static final String NAME = "Draft";
	
	public TimesheetStateDraft() {
		super(NAME);
	}
	
	@Override
	protected Collection<Action<WorkflowableTimesheet>> initActions() {
		Collection<Action<WorkflowableTimesheet>> actions = new HashSet<>();
		actions.add(new TimesheetActionSave());
		return actions;
	}

}
