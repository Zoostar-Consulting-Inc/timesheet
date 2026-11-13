package com.zoostarinc.timesheet.core.request;

import java.time.DayOfWeek;
import java.util.function.Supplier;

import com.zoostarinc.timesheet.core.model.TimesheetRequest;
import com.zoostarinc.timesheet.core.model.WorkflowableTimesheet;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
public class RequestTimesheetSupplier implements Supplier<WorkflowableTimesheet> {
	
	private final String userId;
	
	private final TimesheetRequest request;
	
	@Override
	public WorkflowableTimesheet get() {
		var periodEnding = request.getPeriodEnding();
		if(periodEnding == null || !periodEnding.getDayOfWeek().equals(DayOfWeek.SATURDAY)) {
			throw new IllegalArgumentException("Period ending should be a Saturday!");
		}

		var timesheet = new WorkflowableTimesheet();
		timesheet.setHours(request.getHours());
		timesheet.setPeriodEnding(periodEnding);
		timesheet.setUserId(userId);
		return timesheet;
	}

}
