package com.zoostarinc.timesheet.dao.entity;

import java.util.Objects;
import java.util.UUID;

import com.zoostarinc.timesheet.core.model.TimesheetKey;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import net.zoostar.kd.domain.KeyBasedPersistable;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class TimesheetEntity implements KeyBasedPersistable<TimesheetKey, UUID> {
	
	private UUID id;
	
	private TimesheetKey key;
	
	private Integer hours;

	private String state;

	public TimesheetEntity(TimesheetKey key) {
		this.key = key;
	}

	@Override
	public boolean isNew() {
		return id == null;
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(key);
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
		return Objects.equals(key, other.key);
	}

}
