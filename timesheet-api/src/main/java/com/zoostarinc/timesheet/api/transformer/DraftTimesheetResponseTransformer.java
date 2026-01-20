package com.zoostarinc.timesheet.api.transformer;

import java.time.LocalDate;
import java.util.Collection;
import java.util.HashSet;

import com.zoostarinc.timesheet.api.response.TimesheetResponse;
import com.zoostarinc.timesheet.core.Timesheet;

import lombok.RequiredArgsConstructor;
import net.zoostar.common.transform.Transformer;
import net.zoostar.common.workflow.Action;

@RequiredArgsConstructor
public class DraftTimesheetResponseTransformer implements Transformer<TimesheetResponse> {

	private final LocalDate periodEnding;
	
	private final int hours;
	
	private final String state;
	
	private final Collection<Action<Timesheet>> actions;

	@Override
	public TimesheetResponse transform() {
		Collection<String> strActions = new HashSet<>(1);
		for(var action : actions) {
			strActions.add(action.getName());
		}
		return new TimesheetResponse(periodEnding, hours, state, strActions);
	}

}
