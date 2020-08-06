package app.controller;

import java.sql.Date;

import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import app.ApplicationTests;
import app.entity.User;
import app.controller.UserController;
import app.service.UserServiceImpl;

public class UserControllerTest extends ApplicationTests {

	private MockMvc mockMvc;
	
	@Autowired
	private UserController userController;

	@Autowired
	private UserServiceImpl userService;
	
	@Before
	public void setUp() {
		this.mockMvc = MockMvcBuilders.standaloneSetup(userController).build();
	}
	
	@Test
	public void testPOSTUser() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.post("/api/user")
				.param("name", "Bruna")
				.param("email", "bruna@bruna.com")
				.param("username", "bruna")
				.param("password", "a123456")
				.param("role", "FINAL")
				.param("status", "ATIVO")
				.param("createdAt", "2020-08-04 01:55:54"));
	}
	
	@Test
	public void testGETUsers() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.get("/api/user"))
			.andExpect(MockMvcResultMatchers.status().isOk());
	}
	
	@Test
	public void testGETUser() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.get("/api/user/" + 1))
		.andExpect(MockMvcResultMatchers.status().isOk());
	}
	
	@Test
	public void testPUTUser() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.post("/api/user")
					.param("name", "Cristiana")
					.param("email", "cristiana@cristiana.com")
					.param("username", "cristiana")
					.param("password", "c123456")
					.param("role", "TRIAD")
					.param("status", "INATIVO")
					.param("createdAt", "2020-08-05 03:55:54"));
		this.mockMvc.perform(MockMvcRequestBuilders.put("/api/user/" + 1));
	}
	
	@Test
	public void testDELETEUser() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.delete("/api/user/" + 1));
	}
	
}