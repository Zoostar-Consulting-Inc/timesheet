package com.zoostarinc.timesheet.dao.entity;

import java.time.LocalDate;
import java.util.Objects;
import java.util.UUID;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TimesheetEntity {
	
	private UUID id;
	
	private String userId;

	private LocalDate periodEnding;
	
	private Integer hours;

	private String state;
	
	@Override
	public int hashCode() {
		return Objects.hash(periodEnding, userId);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (!(obj instanceof TimesheetEntity)) {
			return false;
		}
		TimesheetEntity other = (TimesheetEntity) obj;
		return Objects.equals(periodEnding, other.periodEnding) && Objects.equals(userId, other.userId);
	}

}
