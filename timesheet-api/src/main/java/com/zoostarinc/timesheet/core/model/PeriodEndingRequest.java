package com.zoostarinc.timesheet.core.model;

import java.time.LocalDate;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@EqualsAndHashCode
public class PeriodEndingRequest {

	private LocalDate periodEnding;
	
}
