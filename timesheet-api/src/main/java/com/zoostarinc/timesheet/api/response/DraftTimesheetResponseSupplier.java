package com.zoostarinc.timesheet.api.response;

import java.util.Collection;
import java.util.HashSet;
import java.util.function.Supplier;

import com.zoostarinc.timesheet.core.model.TimesheetResponse;
import com.zoostarinc.timesheet.core.model.WorkflowableTimesheet;
import com.zoostarinc.timesheet.dao.entity.TimesheetEntity;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import net.zoostar.common.workflow.State;

@Getter
@RequiredArgsConstructor
public class DraftTimesheetResponseSupplier implements Supplier<TimesheetResponse> {
	
	private final State<WorkflowableTimesheet> draft;
	
	private final TimesheetEntity timesheet;

	@Override
	public TimesheetResponse get() {
		Collection<String> actions = HashSet.newHashSet(2);
		draft.getActions().forEach(action -> actions.add(action.getName()));
		return new TimesheetResponse(timesheet.getPeriodEnding(), timesheet.getHours(), timesheet.getState(), actions);
	}

}
