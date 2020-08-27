package app.controller;

import org.junit.Before;
import org.junit.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import app.ApplicationTests;
import app.controller.UserController;

public class UserControllerTest extends ApplicationTests {

	private MockMvc mockMvc;
	
	@Autowired
	private UserController userController;
	
	@Before
	public void setUp() {
		this.mockMvc = MockMvcBuilders.standaloneSetup(userController).build();
	}
	
	@Test
	public void testPOSTUser() throws Exception {
		String data = "{\"name\": \"Luana\", \"email\": \"luana@luana.com\", \"username\": \"luana\", \"password\": \"l123456\", \"role\": \"FINAL\", \"status\": \"ATIVO\"}";
		
		this.mockMvc.perform(MockMvcRequestBuilders.post("/api/user")
					.contentType(MediaType.APPLICATION_JSON)
					.content(data)
					.accept(MediaType.APPLICATION_JSON))
					.andExpect(MockMvcResultMatchers.status().isCreated());
	}
	
	@Test
	public void testGETUsers() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.get("/api/user"))
			.andExpect(MockMvcResultMatchers.status().isOk());
	}
	
	@Test
	public void testGETUser() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.get("/api/user/{id}", "1"))
			.andExpect(MockMvcResultMatchers.status().isOk());
	}
	
	@Test
	public void testPUTUser() throws Exception {
		String data = "{\"name\": \"Cristiana\", \"email\": \"cristiana@cristiana.com\", \"username\": \"cristiana\", \"password\": \"c123456\", \"role\": \"TRIAD\", \"status\": \"INATIVO\"}";
		
		this.mockMvc.perform(MockMvcRequestBuilders.put("/api/user/{id}", "1")
					.contentType(MediaType.APPLICATION_JSON)
					.content(data)
					.accept(MediaType.APPLICATION_JSON))
					.andExpect(MockMvcResultMatchers.status().isOk());
	}
	
	@Test
	public void testDELETEUser() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.delete("/api/user/{id}", "1"))
					.andExpect(MockMvcResultMatchers.status().isNoContent());
	}
	
}