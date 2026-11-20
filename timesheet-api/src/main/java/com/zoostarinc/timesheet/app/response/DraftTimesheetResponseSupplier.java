package com.zoostarinc.timesheet.app.response;

import java.util.Collection;
import java.util.HashSet;
import java.util.function.Supplier;

import com.zoostarinc.timesheet.core.model.TimesheetResponse;
import com.zoostarinc.timesheet.core.model.WorkflowableTimesheet;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Getter
@RequiredArgsConstructor
public class DraftTimesheetResponseSupplier implements Supplier<TimesheetResponse> {

	private final WorkflowableTimesheet timesheet;

	@Override
	public TimesheetResponse get() {
		log.info("Processing timesheet: {}...", timesheet);
		var state = timesheet.getState();
		Collection<String> actions = HashSet.newHashSet(state.getActions().size());
		state.getActions().forEach(action -> actions.add(action.getName()));
		return new TimesheetResponse(timesheet.getPeriodEnding(), timesheet.getHours(), state.getName(), actions);
	}

}
