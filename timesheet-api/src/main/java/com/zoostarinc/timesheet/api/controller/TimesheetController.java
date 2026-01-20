package com.zoostarinc.timesheet.api.controller;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.zoostarinc.timesheet.api.response.TimesheetResponse;
import com.zoostarinc.timesheet.api.transformer.DraftTimesheetResponseTransformer;
import com.zoostarinc.timesheet.core.Timesheet;

import lombok.RequiredArgsConstructor;
import net.zoostar.common.audit.Timeable;
import net.zoostar.common.workflow.State;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class TimesheetController {

	private final State<Timesheet> timesheetStateDraft;

	@Timeable(threshold = 10)
	@GetMapping(path = "/", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<TimesheetResponse> draft() {
		return ResponseEntity.ok(new DraftTimesheetResponseTransformer(LocalDate.now().with(TemporalAdjusters.next(DayOfWeek.SATURDAY)),
				0, timesheetStateDraft.getName(), timesheetStateDraft.getActions()).transform());
	}

}
