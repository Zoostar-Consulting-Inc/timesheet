package com.zoostarinc.timesheet.core.model;

import java.time.LocalDate;
import java.util.Collection;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class TimesheetResponse extends TimesheetRequest {

	private Collection<String> actions;

	public TimesheetResponse(LocalDate periodEnding, int hours, String state, Collection<String> actions) {
		super(periodEnding, hours, state);
		this.actions = actions;
	}

}
