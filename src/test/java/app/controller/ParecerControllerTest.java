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
import app.entity.Parecer;
import app.controller.ParecerController;
import app.service.ParecerServiceImpl;

public class ParecerControllerTest extends ApplicationTests {

	private MockMvc mockMvc;
	
	@Autowired
	private ParecerController parecerController;

	@Autowired
	private ParecerServiceImpl parecerService;
	
	@Before
	public void setUp() {
		this.mockMvc = MockMvcBuilders.standaloneSetup(parecerController).build();
	}
	
	@Test
	public void testPOSTParecer() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.post("/api/parecer")
				.param("comentario", "Parecer Teste 2")
				.param("status", "Ativo")
				.param("idprocesso", "1")
				.param("iduser", "1")
				.param("createdAt", "2020-05-04 05:25:10"));
	}
	
	@Test
	public void testGETPareceres() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.get("/api/parecer"))
			.andExpect(MockMvcResultMatchers.status().isOk());
	}
	
	@Test
	public void testGETParecer() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.get("/api/parecer/" + 1))
		.andExpect(MockMvcResultMatchers.status().isOk());
	}
	
	@Test
	public void testPUTParecer() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.post("/api/parecer")
					.param("comentario", "Parecer Teste 3")
					.param("status", "Inativo")
					.param("idprocesso", "1")
					.param("iduser", "1")
					.param("createdAt", "2020-08-05 06:25:10"));
		this.mockMvc.perform(MockMvcRequestBuilders.put("/api/parecer/" + 1));
	}
	
	@Test
	public void testDELETEParecer() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.delete("/api/parecer/" + 1));
	}
		
}