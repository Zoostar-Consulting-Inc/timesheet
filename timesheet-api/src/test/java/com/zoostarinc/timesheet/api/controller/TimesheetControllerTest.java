package com.zoostarinc.timesheet.api.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.oidcLogin;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;

import net.zoostar.common.StringWrapper;

class TimesheetControllerTest extends AbstractCommonTest {

	@Test
	void testGetDraftOk() throws Exception {
		// given
		String url = "/api/";

		// when
		var response = client.perform(get(url).with(oidcLogin().oidcUser(oidcUser()))).andReturn().getResponse();

		// then
		assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
		var object = om.readValue(response.getContentAsString(), StringWrapper.class);
		assertThat(object.getValue()).isSameAs(object.toString()).isEqualTo("Draft Timesheet comming soon...");
	}

}
