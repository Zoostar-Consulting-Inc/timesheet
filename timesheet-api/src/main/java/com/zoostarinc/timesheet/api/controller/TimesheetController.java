package com.zoostarinc.timesheet.api.controller;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.ZoneOffset;
import java.time.temporal.TemporalAdjusters;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.zoostarinc.timesheet.api.config.SecurityConfig;
import com.zoostarinc.timesheet.api.response.DraftTimesheetResponseSupplier;
import com.zoostarinc.timesheet.core.model.PeriodEndingRequest;
import com.zoostarinc.timesheet.core.model.TimesheetRequest;
import com.zoostarinc.timesheet.core.model.TimesheetResponse;
import com.zoostarinc.timesheet.core.model.WorkflowableTimesheet;
import com.zoostarinc.timesheet.core.request.RequestTimesheetSupplier;
import com.zoostarinc.timesheet.service.TimesheetService;
import com.zoostarinc.timesheet.spi.workflow.action.impl.TimesheetActionSave;

import lombok.RequiredArgsConstructor;
import net.zoostar.common.audit.Timeable;
import net.zoostar.common.web.response.SuccessfulRequestLoggerResponseEntity;
import net.zoostar.common.workflow.State;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class TimesheetController {

	private final ObjectMapper objectMapper;

	private final State<WorkflowableTimesheet> timesheetStateDraft;

	private final TimesheetService timesheetManager;

	@Timeable(threshold = 10)
	@GetMapping(path = "/draft", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<TimesheetResponse> draft() {
		return ResponseEntity.ok(new DraftTimesheetResponseSupplier(timesheetStateDraft,
				timesheetManager.update(
						new RequestTimesheetSupplier(SecurityConfig.getCurrentSubject(),
								new TimesheetRequest(LocalDate.now(ZoneOffset.UTC)
										.with(TemporalAdjusters.next(DayOfWeek.SATURDAY)), 0)),
						TimesheetActionSave.NAME))
				.get());
	}

	@Timeable(threshold = 10)
	@PostMapping(path = "/draft", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<TimesheetResponse> draft(@RequestBody(required = false) PeriodEndingRequest request) {
		return new SuccessfulRequestLoggerResponseEntity<TimesheetResponse, PeriodEndingRequest>(
				new DraftTimesheetResponseSupplier(timesheetStateDraft,
						timesheetManager
								.update(new RequestTimesheetSupplier(SecurityConfig.getCurrentSubject(),
										new TimesheetRequest(request == null
												? LocalDate.now(ZoneOffset.UTC)
														.with(TemporalAdjusters.next(DayOfWeek.SATURDAY))
												: request.getPeriodEnding(), 0)),
										TimesheetActionSave.NAME))
						.get(),
				request, objectMapper);
	}

	@Timeable(threshold = 10)
	@PostMapping(path = "/update/{action}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<TimesheetResponse> update(@PathVariable String action,
			@RequestBody TimesheetRequest request) {
		return new SuccessfulRequestLoggerResponseEntity<TimesheetResponse, TimesheetRequest>(
				new DraftTimesheetResponseSupplier(timesheetStateDraft,
						timesheetManager.update(
								new RequestTimesheetSupplier(SecurityConfig.getCurrentSubject(), request), action))
						.get(),
				request, objectMapper);
	}

}
