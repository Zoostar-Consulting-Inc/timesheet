package com.zoostarinc.timesheet.service.impl;

import java.util.Iterator;
import java.util.Map;
import java.util.UUID;
import java.util.function.Supplier;

import org.springframework.stereotype.Service;

import com.zoostarinc.timesheet.core.model.TimesheetKey;
import com.zoostarinc.timesheet.core.model.WorkflowableTimesheet;
import com.zoostarinc.timesheet.dao.entity.TimesheetEntity;
import com.zoostarinc.timesheet.service.TimesheetService;

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
public class DefaultTimesheetService implements TimesheetService {

	private final Map<String, State<WorkflowableTimesheet>> states;

	private final KeyBasedRepository<TimesheetEntity, TimesheetKey, UUID> timesheetRepository;

	private final State<WorkflowableTimesheet> stateDraft;

	@Override
	public WorkflowableTimesheet update(Supplier<WorkflowableTimesheet> requestTimesheetSupplier, String act) {
		var timesheet = requestTimesheetSupplier.get();
		var key = new TimesheetKey(timesheet.getUserId(), timesheet.getPeriodEnding());
		var entity = timesheetRepository.findByKey(key)
				.orElseGet(() -> createDraftTimesheet(timesheetRepository, timesheet, stateDraft.getName()));
		var state = states.get(entity.getState().toUpperCase());

		boolean actionMatched = false;
		var actions = state.getActions();
		for (Iterator<Action<WorkflowableTimesheet>> it = actions.iterator(); it.hasNext() && !actionMatched;) {
			var action = it.next();
			if (action.getName().equalsIgnoreCase(act)) {
				actionMatched = true;
				action.execute(timesheet);
			}
		}
		if (!actionMatched) {
			log.warn("Action {} asked to perform is not consistent to current state {}", act, entity.getState());
			throw new IllegalArgumentException("No matching action found for given state!");
		}
		log.info("Received request to update entity: {}...", entity);
		entity.setHours(timesheet.getHours());
		entity.setState(timesheet.getState().getName());
		return timesheet;
	}

	protected TimesheetEntity createDraftTimesheet(
			KeyBasedRepository<TimesheetEntity, TimesheetKey, UUID> timesheetRepository,
			WorkflowableTimesheet timesheet, String defaultStateName) {
		var persistable = new TimesheetEntity(new TimesheetKey(timesheet.getUserId(), timesheet.getPeriodEnding()));
		persistable.setState(timesheet.getState() == null ? defaultStateName : timesheet.getState().getName());
		timesheetRepository.save(persistable);
		return persistable;
	}

}
