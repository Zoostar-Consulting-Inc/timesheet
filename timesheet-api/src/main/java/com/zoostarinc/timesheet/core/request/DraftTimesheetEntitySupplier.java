package com.zoostarinc.timesheet.core.request;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.temporal.TemporalAdjusters;
import java.util.Map;
import java.util.function.Supplier;

import com.zoostarinc.timesheet.core.model.WorkflowableTimesheet;
import com.zoostarinc.timesheet.dao.entity.TimesheetEntity;
import com.zoostarinc.timesheet.service.impl.DefaultTimesheetService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.zoostar.common.workflow.State;

@Slf4j
@RequiredArgsConstructor
public class DraftTimesheetEntitySupplier implements Supplier<TimesheetEntity> {
	
	private final String userId;
	
	private final Map<String, State<WorkflowableTimesheet>> states;

	@Override
	public TimesheetEntity get() {
		var persistable = new TimesheetEntity();
		persistable.setHours(0);
		persistable.setPeriodEnding(LocalDate.now(ZoneId.of(DefaultTimesheetService.ZONE_UTC)).with(TemporalAdjusters.next(DayOfWeek.SATURDAY)));
		persistable.setState(states.get(DefaultTimesheetService.STATE_DRAFT).getName());
		persistable.setUserId(userId);
		log.info("Received request to save Timesheet: {}...", persistable);
		return persistable;
	}

}
