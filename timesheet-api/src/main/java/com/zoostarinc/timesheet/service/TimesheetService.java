package com.zoostarinc.timesheet.service;

import java.util.Map;
import java.util.function.Supplier;

import com.zoostarinc.timesheet.core.model.WorkflowableTimesheet;
import com.zoostarinc.timesheet.dao.entity.TimesheetEntity;

import net.zoostar.common.workflow.State;

public interface TimesheetService {
	Map<String, State<WorkflowableTimesheet>> getStates();
	TimesheetEntity create(Supplier<TimesheetEntity> draftTimesheetEntitySupplier);
	TimesheetEntity update(Supplier<WorkflowableTimesheet> requestTimesheetSupplier, String action);
}
