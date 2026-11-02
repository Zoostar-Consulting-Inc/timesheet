package com.zoostarinc.timesheet.core.model;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TimesheetRequest {

	private LocalDate periodEnding;
	
	private int hours;
	
	private String state;
	
}
