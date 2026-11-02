package com.zoostarinc.timesheet.spi.workflow.action.impl;

import com.zoostarinc.timesheet.core.model.WorkflowableTimesheet;
import com.zoostarinc.timesheet.spi.workflow.state.impl.AbstractTimeheetState;
import com.zoostarinc.timesheet.spi.workflow.state.impl.TimesheetStateDraft;

import net.zoostar.common.workflow.Action;

public class TimesheetActionSave implements Action<WorkflowableTimesheet> {

	public static final String NAME = "Save";
	
	@Override
	public String getName() {
		return NAME;
	}

	@Override
	public void execute(WorkflowableTimesheet timesheet) {
		timesheet.setState(AbstractTimeheetState.getState(TimesheetStateDraft.NAME));
	}

}
