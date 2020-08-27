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
import app.controller.ParecerController;

public class ParecerControllerTest extends ApplicationTests {

	private MockMvc mockMvc;
	
	@Autowired
	private ParecerController parecerController;
	
	@Before
	public void setUp() {
		this.mockMvc = MockMvcBuilders.standaloneSetup(parecerController).build();
	}
	
	@Test
	public void testGETPareceres() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.get("/api/parecer")
					.contentType(MediaType.APPLICATION_JSON)
					.accept(MediaType.APPLICATION_JSON))
					.andExpect(MockMvcResultMatchers.status().isOk());
	}
	
	@Test
	public void testGETParecer() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.get("/api/parecer/{id}", "1")
					.contentType(MediaType.APPLICATION_JSON)
					.accept(MediaType.APPLICATION_JSON))
					.andExpect(MockMvcResultMatchers.status().isOk());
	}
		
}