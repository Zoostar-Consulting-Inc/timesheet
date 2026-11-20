package com.zoostarinc.timesheet.service;

import java.util.Map;
import java.util.function.Supplier;

import com.zoostarinc.timesheet.core.model.WorkflowableTimesheet;

import net.zoostar.common.workflow.State;

public interface TimesheetService {
	Map<String, State<WorkflowableTimesheet>> getStates();
	WorkflowableTimesheet update(Supplier<WorkflowableTimesheet> requestTimesheetSupplier, String action);
}
