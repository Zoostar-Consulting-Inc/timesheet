package com.zoostarinc.timesheet.app.controller;

import java.util.Optional;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.zoostarinc.timesheet.app.config.SecurityConfig;
import com.zoostarinc.timesheet.app.response.DraftTimesheetResponseSupplier;
import com.zoostarinc.timesheet.core.model.TimesheetRequest;
import com.zoostarinc.timesheet.core.model.TimesheetResponse;
import com.zoostarinc.timesheet.core.request.RequestTimesheetSupplier;
import com.zoostarinc.timesheet.service.TimesheetService;

import lombok.RequiredArgsConstructor;
import net.zoostar.common.audit.Timeable;
import net.zoostar.common.web.response.SuccessfulRequestLoggerResponseEntity;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class TimesheetController {

	public static final String DEFAULT_ACTION_NAME = "Save";

	private final ObjectMapper objectMapper;

	private final TimesheetService timesheetManager;

	@Timeable(threshold = 10)
	@GetMapping(path = "/draft", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<TimesheetResponse> draft() {
		return ResponseEntity.ok(new DraftTimesheetResponseSupplier(timesheetManager.update(
				new RequestTimesheetSupplier(SecurityConfig.getCurrentSubject(), Optional.empty()),
				DEFAULT_ACTION_NAME)).get());
	}

	@Timeable(threshold = 10)
	@PostMapping(path = "/draft", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<TimesheetResponse> draft(@RequestBody(required = false) TimesheetRequest request) {
		return new SuccessfulRequestLoggerResponseEntity<TimesheetResponse, TimesheetRequest>(
				new DraftTimesheetResponseSupplier(timesheetManager.update(
						new RequestTimesheetSupplier(SecurityConfig.getCurrentSubject(), Optional.ofNullable(request)),
						DEFAULT_ACTION_NAME)).get(),
				request, objectMapper);
	}

	@Timeable(threshold = 10)
	@PostMapping(path = "/update/{action}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<TimesheetResponse> update(@PathVariable String action,
			@RequestBody TimesheetRequest request) {
		return new SuccessfulRequestLoggerResponseEntity<TimesheetResponse, TimesheetRequest>(
				new DraftTimesheetResponseSupplier(timesheetManager.update(
						new RequestTimesheetSupplier(SecurityConfig.getCurrentSubject(), Optional.of(request)), action))
						.get(),
				request, objectMapper);
	}

}
