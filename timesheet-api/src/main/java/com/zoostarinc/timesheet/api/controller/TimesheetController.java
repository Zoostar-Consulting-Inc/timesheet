package com.zoostarinc.timesheet.api.controller;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.zoostarinc.timesheet.api.response.TimesheetResponse;

import net.zoostar.common.audit.Timeable;

@RestController
@RequestMapping("/api")
public class TimesheetController {

	@Timeable(threshold = 10)
	@GetMapping(path = "/", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<TimesheetResponse> draft() {
		return ResponseEntity.ok(new TimesheetResponse("Draft Timesheet comming soon..."));
	}
	
}
