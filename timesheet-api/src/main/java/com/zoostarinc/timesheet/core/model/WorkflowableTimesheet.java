package com.zoostarinc.timesheet.core.model;

import java.time.LocalDate;

import lombok.Getter;
import lombok.Setter;
import net.zoostar.common.workflow.State;
import net.zoostar.common.workflow.Workflowable;

@Getter
@Setter
public class WorkflowableTimesheet implements Workflowable {
	
	private String userId;
	
	private LocalDate periodEnding;
	
	private Integer hours;

	private State<WorkflowableTimesheet> state;
	
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("WorkflowableTimesheet [userId=").append(userId).append(", periodEnding=").append(periodEnding)
				.append(", hours=").append(hours).append(", state=").append(state.getName()).append("]");
		return builder.toString();
	}
	
}
