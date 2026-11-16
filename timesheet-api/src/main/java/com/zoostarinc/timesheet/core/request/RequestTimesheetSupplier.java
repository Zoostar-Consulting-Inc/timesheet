package com.zoostarinc.timesheet.core.request;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.ZoneOffset;
import java.time.temporal.TemporalAdjusters;
import java.util.Optional;
import java.util.function.Supplier;

import com.zoostarinc.timesheet.core.model.TimesheetRequest;
import com.zoostarinc.timesheet.core.model.WorkflowableTimesheet;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
public class RequestTimesheetSupplier implements Supplier<WorkflowableTimesheet> {

	private final String userId;

	private final Optional<TimesheetRequest> request;

	@Override
	public WorkflowableTimesheet get() {
		var timesheet = new WorkflowableTimesheet();
		timesheet.setUserId(userId);
		if (request.isEmpty()) {
			timesheet.setHours(0);
			timesheet.setPeriodEnding(LocalDate.now(ZoneOffset.UTC).with(TemporalAdjusters.next(DayOfWeek.SATURDAY)));
		} else {
			timesheet.setHours(request.get().getHours());
			var periodEnding = request.get().getPeriodEnding();
			if (periodEnding == null || !periodEnding.getDayOfWeek().equals(DayOfWeek.SATURDAY)) {
				throw new IllegalArgumentException("Period ending should be a Saturday!");
			}
			timesheet.setPeriodEnding(periodEnding);
		}
		return timesheet;
	}

}
