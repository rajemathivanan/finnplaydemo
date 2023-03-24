package com.finnplay.demo.app.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestBuilders.formLogin;
import static org.springframework.security.test.web.servlet.response.SecurityMockMvcResultMatchers.authenticated;
import static org.springframework.security.test.web.servlet.response.SecurityMockMvcResultMatchers.unauthenticated;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest
@AutoConfigureMockMvc
public class AppUserControllerTest {

    @Autowired
	private MockMvc mockMvc;

    @Test
	public void accessPublicIndexPage() throws Exception {
		// @formatter:off
		this.mockMvc.perform(get("/index"))
				.andExpect(status().isOk());
		// @formatter:on
	}

    @Test
	public void accessPublicSignupPage() throws Exception {
		// @formatter:off
		this.mockMvc.perform(get("/signup"))
				.andExpect(status().isOk());
		// @formatter:on
	}

	@Test
	public void accessPrivatePage() throws Exception {
		// @formatter:off
		this.mockMvc.perform(get("/userinfo"))
		.andExpect(status().is3xxRedirection())
		.andReturn();
		// @formatter:on
	}

		@Test
		public void testUserLoginWithSuccess() throws Exception {
			this.mockMvc.perform(formLogin().user("email","test@test.com").password("P@$$word123"))
				.andExpect(authenticated());
		
		}
		@Test
		public void testInvalidUserLogin() throws Exception {
			this.mockMvc.perform(formLogin().user("email","test@test.com").password("abcdefg"))
				.andExpect(unauthenticated());
		
		}

}
