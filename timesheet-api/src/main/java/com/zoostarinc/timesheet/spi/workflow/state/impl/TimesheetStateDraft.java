package com.zoostarinc.timesheet.spi.workflow.state.impl;

import java.util.Collection;
import java.util.HashSet;

import org.springframework.stereotype.Component;

import com.zoostarinc.timesheet.core.Timesheet;
import com.zoostarinc.timesheet.spi.workflow.action.impl.TimesheetActionSave;

import net.zoostar.common.workflow.Action;

@Component
public class TimesheetStateDraft extends AbstractTimeheetState {

	public static final String NAME = "DRAFT";
	
	public TimesheetStateDraft() {
		super(NAME);
	}
	
	@Override
	protected Collection<Action<Timesheet>> initActions() {
		Collection<Action<Timesheet>> actions = new HashSet<>();
		actions.add(new TimesheetActionSave());
		return actions;
	}

}
