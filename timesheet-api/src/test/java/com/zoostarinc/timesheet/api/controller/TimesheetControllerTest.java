package com.zoostarinc.timesheet.api.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.oidcLogin;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;

import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;

import com.zoostarinc.timesheet.api.response.TimesheetResponse;
import com.zoostarinc.timesheet.spi.workflow.state.impl.TimesheetStateDraft;

class TimesheetControllerTest extends AbstractCommonTest {

	@Test
	void testGetDraftOk() throws Exception {
		// given
		String url = "/api/";

		// when
		var response = client.perform(get(url).with(oidcLogin().oidcUser(oidcUser()))).andReturn().getResponse();

		// then
		assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
		var actual = om.readValue(response.getContentAsString(), TimesheetResponse.class);

		assertThat(actual.getClass()).isEqualTo(TimesheetResponse.class);
		assertThat(actual.getHours()).isZero();
		assertThat(actual.getState()).isEqualTo(TimesheetStateDraft.NAME);
		assertThat(actual.getPeriodEnding())
				.isEqualTo(LocalDate.now().with(TemporalAdjusters.next(DayOfWeek.SATURDAY)));
	}

}
