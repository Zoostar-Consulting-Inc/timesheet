package com.zoostarinc.timesheet.service.impl;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.UUID;
import java.util.function.Supplier;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Service;

import com.zoostarinc.timesheet.core.model.TimesheetKey;
import com.zoostarinc.timesheet.core.model.WorkflowableTimesheet;
import com.zoostarinc.timesheet.dao.entity.TimesheetEntity;
import com.zoostarinc.timesheet.service.TimesheetService;
import com.zoostarinc.timesheet.spi.workflow.state.impl.TimesheetStateDraft;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.zoostar.common.workflow.Action;
import net.zoostar.common.workflow.State;
import net.zoostar.kd.data.KeyBasedRepository;

@Slf4j
@Getter
@Service
@RequiredArgsConstructor
public class DefaultTimesheetService implements TimesheetService, InitializingBean {
	
	public static final String ZONE_UTC = "UTC";
	

	private Map<String, State<WorkflowableTimesheet>> states;
	
	private final State<WorkflowableTimesheet> timesheetStateDraft;
	
	private final KeyBasedRepository<TimesheetEntity, TimesheetKey, UUID> timesheetRepository;

	@Override
	public void afterPropertiesSet() throws Exception {
		initializeStates();
	}

	protected void initializeStates() {
		states = new HashMap<>();
		states.put(TimesheetStateDraft.NAME.toUpperCase(), timesheetStateDraft);
	}

	@Override
	public TimesheetEntity update(Supplier<WorkflowableTimesheet> requestTimesheetSupplier, String act) {
		var request = requestTimesheetSupplier.get();
		var key = new TimesheetKey(request.getUserId(), request.getPeriodEnding());
		var entity = timesheetRepository.findByKey(key).orElseGet(() -> createDraftTimesheet(timesheetRepository, request));
		var state = states.get(entity.getState().toUpperCase());
		
		boolean actionMatched = false;
		var actions = state.getActions();
		for(Iterator<Action<WorkflowableTimesheet>> it = actions.iterator(); it.hasNext() && !actionMatched;) {
			var action = it.next();
			if(action.getName().equalsIgnoreCase(act)) {
				actionMatched = true;
				action.execute(request);
			}
		}
		if(!actionMatched) {
			log.warn("Action {} asked to perform is not consistent to current state {}", act, entity.getState());
			throw new IllegalArgumentException("No matching action found for given state!");
		}
		log.info("Received request to update Workflowable: {}...", request);
		entity.setHours(request.getHours());
		
		return entity;
	}

	protected TimesheetEntity createDraftTimesheet(KeyBasedRepository<TimesheetEntity, TimesheetKey, UUID> timesheetRepository, WorkflowableTimesheet timesheet) {
		var persistable = new TimesheetEntity(new TimesheetKey(timesheet.getUserId(), timesheet.getPeriodEnding()));
		persistable.setState(timesheet.getState() == null ? TimesheetStateDraft.NAME : timesheet.getState().getName());
		timesheetRepository.save(persistable);
		return persistable;
	}

}
