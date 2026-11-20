package com.zoostarinc.timesheet.workflow.impl;

import java.util.Collection;
import java.util.Objects;

import com.zoostarinc.timesheet.core.model.WorkflowableTimesheet;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import net.zoostar.common.workflow.Action;
import net.zoostar.common.workflow.State;

@Getter
@RequiredArgsConstructor
public class GenericTimesheetState implements State<WorkflowableTimesheet> {

	private final String name;
	
	private final Collection<Action<WorkflowableTimesheet>> actions;

	@Override
	public int hashCode() {
		return Objects.hash(name);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (!(obj instanceof GenericTimesheetState)) {
			return false;
		}
		GenericTimesheetState other = (GenericTimesheetState) obj;
		return Objects.equals(name, other.name);
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("DefaultTimesheetState [name=").append(name).append("]");
		return builder.toString();
	}

}
