package com.zoostarinc.timesheet.service.impl;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.function.Supplier;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Service;

import com.zoostarinc.timesheet.core.model.WorkflowableTimesheet;
import com.zoostarinc.timesheet.dao.entity.TimesheetEntity;
import com.zoostarinc.timesheet.service.TimesheetService;
import com.zoostarinc.timesheet.spi.workflow.state.impl.TimesheetStateDraft;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import net.zoostar.common.workflow.State;

@Slf4j
@Getter
@Service
public class DefaultTimesheetService implements TimesheetService, InitializingBean {
	
	public static final String ZONE_UTC = "UTC";
	
	public static final String STATE_DRAFT = "DRAFT";
	
	private Map<String, State<WorkflowableTimesheet>> states;

	@Override
	public void afterPropertiesSet() throws Exception {
		initializeStates();
	}

	protected void initializeStates() {
		states = new HashMap<>();
		states.put(STATE_DRAFT, new TimesheetStateDraft());
	}

	@Override
	public TimesheetEntity create(Supplier<TimesheetEntity> draftTimesheetEntitySupplier) {
		var entity = draftTimesheetEntitySupplier.get();
		entity.setId(UUID.randomUUID()); // Create temporary entity
		log.info("Timesheet saved: {}.", entity);
		return entity;
	}

	@Override
	public TimesheetEntity update(Supplier<WorkflowableTimesheet> requestTimesheetSupplier, String actionToTake) {
		var timesheet = requestTimesheetSupplier.get();
		var currentState = timesheet.getState();
		for(var availableAction : currentState.getActions()) {
			if(actionToTake.equalsIgnoreCase(availableAction.getName())) {
				availableAction.execute(timesheet);
				break;
			}
		}
		
		var entity = new TimesheetEntity();
		entity.setHours(timesheet.getHours());
		entity.setId(UUID.randomUUID());
		entity.setPeriodEnding(timesheet.getPeriodEnding());
		entity.setState(timesheet.getState().getName());
		entity.setUserId(timesheet.getUserId());
		return entity;
	}

}
