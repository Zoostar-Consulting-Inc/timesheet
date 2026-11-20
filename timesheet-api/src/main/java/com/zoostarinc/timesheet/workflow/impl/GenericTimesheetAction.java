package com.zoostarinc.timesheet.workflow.impl;

import com.zoostarinc.timesheet.core.model.WorkflowableTimesheet;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import net.zoostar.common.workflow.Action;
import net.zoostar.common.workflow.State;

@Getter
@ToString
@EqualsAndHashCode
@RequiredArgsConstructor
public class GenericTimesheetAction implements Action<WorkflowableTimesheet> {

	private final String name;
	
	private final State<WorkflowableTimesheet> state;
	
	@Override
	public void execute(WorkflowableTimesheet workflowable) {
		workflowable.setState(state);
	}
	
}
