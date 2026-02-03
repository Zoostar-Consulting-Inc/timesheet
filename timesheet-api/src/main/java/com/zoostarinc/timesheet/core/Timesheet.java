package com.zoostarinc.timesheet.core;

import lombok.Getter;
import lombok.Setter;
import net.zoostar.common.workflow.State;
import net.zoostar.common.workflow.Workflowable;

@Getter
@Setter
public class Timesheet implements Workflowable {

	private State<Timesheet> state;
	
}
