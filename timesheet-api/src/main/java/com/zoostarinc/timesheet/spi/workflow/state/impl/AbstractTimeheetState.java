package com.zoostarinc.timesheet.spi.workflow.state.impl;

import java.util.Collection;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

import com.zoostarinc.timesheet.core.model.WorkflowableTimesheet;

import lombok.Getter;
import net.zoostar.common.workflow.Action;
import net.zoostar.common.workflow.State;

@Getter
public abstract class AbstractTimeheetState implements State<WorkflowableTimesheet> {

	private final String name;

	private final Collection<Action<WorkflowableTimesheet>> actions;
	
	private static final Map<String, AbstractTimeheetState> TIMESHEET_STATES = new ConcurrentHashMap<>();

	protected AbstractTimeheetState(String name) {
		this.name = name;
		this.actions = initActions();
		TIMESHEET_STATES.put(name, this); //ConcurrentHashMap does not support null so no null-check needed here.
	}

	protected abstract Collection<Action<WorkflowableTimesheet>> initActions();
	
	public static AbstractTimeheetState getState(String name) {
		return TIMESHEET_STATES.get(name);
	}

	@Override
	public int hashCode() {
		return Objects.hash(name);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (!(obj instanceof AbstractTimeheetState)) {
			return false;
		}
		AbstractTimeheetState other = (AbstractTimeheetState) obj;
		return Objects.equals(name, other.name);
	}
}
