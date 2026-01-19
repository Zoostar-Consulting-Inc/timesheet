package com.zoostarinc.timesheet.api.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.oidcLogin;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;

class SwaggerControllerTest extends AbstractCommonTest {

	@Test
	void testGreeting() throws Exception {
		// given
		String url = "/";

		// when
		var response = client.perform(get(url).with(oidcLogin().oidcUser(oidcUser()))).andReturn().getResponse();

		// then
		assertThat(response.getStatus()).isEqualTo(HttpStatus.FOUND.value());
	}

}
