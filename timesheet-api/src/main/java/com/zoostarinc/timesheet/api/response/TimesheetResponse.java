package com.zoostarinc.timesheet.api.response;

import java.time.LocalDate;
import java.util.Collection;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@RequiredArgsConstructor
public class TimesheetResponse {

	private final LocalDate periodEnding;
	
	private final int hours;
	
	private final String state;
	
	private final Collection<String> actions;
	
}
