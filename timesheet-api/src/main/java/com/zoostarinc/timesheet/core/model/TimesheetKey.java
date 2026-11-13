package com.zoostarinc.timesheet.core.model;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Getter
@Setter
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class TimesheetKey {
	
	private String userId;

	private LocalDate periodEnding;
	
	public static void main(String[] args) {
		var key1 = new TimesheetKey("userId", LocalDate.now());
		log.info("Key 1: {}", key1);
		var key2 = new TimesheetKey("userId", LocalDate.now());
		log.info("Key 2: {}", key2);
		log.info("Key 1 is equal to Key 2? {}", key1.equals(key2));
	}

}
