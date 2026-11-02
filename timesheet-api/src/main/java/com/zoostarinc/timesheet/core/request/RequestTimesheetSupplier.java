package com.zoostarinc.timesheet.core.request;

import java.util.Map;
import java.util.function.Supplier;

import org.springframework.util.StringUtils;

import com.zoostarinc.timesheet.core.model.TimesheetRequest;
import com.zoostarinc.timesheet.core.model.WorkflowableTimesheet;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.zoostar.common.workflow.State;

@Slf4j
@RequiredArgsConstructor
public class RequestTimesheetSupplier implements Supplier<WorkflowableTimesheet> {
	
	private final String userId;
	
	private final TimesheetRequest request;
	
	private final Map<String, State<WorkflowableTimesheet>> states;

	@Override
	public WorkflowableTimesheet get() {
		var currentState = request.getState();
		if(!StringUtils.hasText(currentState)) {
			throw new IllegalArgumentException("Current state may not be null!");
		}
		
		var timesheet = new WorkflowableTimesheet();
		timesheet.setHours(request.getHours());
		timesheet.setPeriodEnding(request.getPeriodEnding());
		timesheet.setUserId(userId);
		
		for(var entry : states.entrySet()) {
			if(entry.getKey().equalsIgnoreCase(currentState.trim())) {
				timesheet.setState(entry.getValue());
				break;
			}
		}
		if(timesheet.getState() == null) {
			throw new IllegalArgumentException("Current state of workflowable is invalid!");
		}
		
		log.info("Received request to update Workflowable: {}...", timesheet);
		return timesheet;
	}

}
