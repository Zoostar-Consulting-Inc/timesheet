package com.zoostarinc.timesheet.api.controller;

import java.time.Instant;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.core.oidc.OidcIdToken;
import org.springframework.security.oauth2.core.oidc.OidcUserInfo;
import org.springframework.security.oauth2.core.oidc.StandardClaimNames;
import org.springframework.security.oauth2.core.oidc.user.DefaultOidcUser;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;


@SpringBootTest
@AutoConfigureMockMvc
public abstract class AbstractCommonTest {

	@Autowired
	MockMvc client;

	@Autowired
	ObjectMapper om;

	protected OidcUser oidcUser() {
		Map<String, Object> claims = new HashMap<>();
		claims.put(StandardClaimNames.SUB, "testuser");
		claims.put(StandardClaimNames.GIVEN_NAME, "junit");
		claims.put(StandardClaimNames.EMAIL, "test@example.com");
		OidcIdToken idToken = new OidcIdToken("tokenValue", Instant.now(), Instant.now().plusSeconds(3600), claims);
		OidcUserInfo userInfo = new OidcUserInfo(claims);
		return new DefaultOidcUser(Collections.singletonList(new SimpleGrantedAuthority("ROLE_USER")), idToken,
				userInfo);
	}

}
