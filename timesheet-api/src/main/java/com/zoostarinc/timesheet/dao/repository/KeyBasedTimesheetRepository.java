package com.zoostarinc.timesheet.dao.repository;

import java.util.UUID;

import org.springframework.stereotype.Repository;

import com.zoostarinc.timesheet.core.model.TimesheetKey;
import com.zoostarinc.timesheet.dao.entity.TimesheetEntity;

import lombok.extern.slf4j.Slf4j;
import net.zoostar.kd.data.AbstractKeyBasedRepository;

@Slf4j
@Repository
public class KeyBasedTimesheetRepository extends AbstractKeyBasedRepository<TimesheetEntity, TimesheetKey, UUID> {

	@Override
	public <S extends TimesheetEntity> S save(S persistable) {
		log.info("Saving persistable: {}...", persistable);
		if (persistable.isNew()) {
			persistable.setId(UUID.randomUUID());
		}

		recordsById.put(persistable.getId(), persistable);
		recordsByKey.put(persistable.getKey(), persistable);
		return persistable;
	}

}
