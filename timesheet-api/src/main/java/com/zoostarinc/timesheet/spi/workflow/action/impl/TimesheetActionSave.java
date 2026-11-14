package com.zoostarinc.timesheet.spi.workflow.action.impl;

import com.zoostarinc.timesheet.core.model.WorkflowableTimesheet;

import lombok.RequiredArgsConstructor;
import net.zoostar.common.workflow.Action;
import net.zoostar.common.workflow.State;

@RequiredArgsConstructor
public class TimesheetActionSave implements Action<WorkflowableTimesheet> {

	public static final String NAME = "Save";
	
	private final State<WorkflowableTimesheet> draft;
	
	@Override
	public String getName() {
		return NAME;
	}

	@Override
	public void execute(WorkflowableTimesheet timesheet) {
		timesheet.setState(draft);
	}

}
