package com.zoostarinc.timesheet.spi.workflow.state.impl;

import java.util.Collection;
import java.util.Objects;

import com.zoostarinc.timesheet.core.model.WorkflowableTimesheet;

import lombok.Getter;
import net.zoostar.common.workflow.Action;
import net.zoostar.common.workflow.State;

@Getter
public abstract class AbstractTimeheetState implements State<WorkflowableTimesheet> {
	
	private final String name;

	private final Collection<Action<WorkflowableTimesheet>> actions;
	
	protected AbstractTimeheetState(String name) {
		this.name = name;
		this.actions = initActions();
	}

	protected abstract Collection<Action<WorkflowableTimesheet>> initActions();
	
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
