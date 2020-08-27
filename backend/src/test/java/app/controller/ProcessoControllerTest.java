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
import app.controller.ProcessoController;

public class ProcessoControllerTest extends ApplicationTests {

	private MockMvc mockMvc;
	
	@Autowired
	private ProcessoController processoController;

	@Before
	public void setUp() {
		this.mockMvc = MockMvcBuilders.standaloneSetup(processoController).build();
	}
	
	@Test
	public void testPOSTProcesso() throws Exception {
		String data = "{\"criador\": {\"id\": 2, \"name\": \"Bruna\", \"role\": \"TRIAD\", \"username\": \"bruna\"}, \"descricao\": \"Descrição Teste\", \"pareceres\": null, \"status\": \"ABERTO\", \"titulo\": \"Processo 001\", \"users\": null}";
		
		this.mockMvc.perform(MockMvcRequestBuilders.post("/api/processo")
					.contentType(MediaType.APPLICATION_JSON)
					.content(data)
					.accept(MediaType.APPLICATION_JSON))
					.andExpect(MockMvcResultMatchers.status().isCreated());
	}
	
	@Test
	public void testGETProcessos() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.get("/api/processo"))
					.andExpect(MockMvcResultMatchers.status().isOk());
	}
	
	@Test
	public void testGETProcesso() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.get("/api/processo/{id}", "1"))
					.andExpect(MockMvcResultMatchers.status().isOk());
	}
	
	@Test
	public void testPUTProcesso() throws Exception {
		String data = "{\"criador\": {\"id\": 2, \"name\": \"Bruna\", \"role\": \"TRIAD\", \"username\": \"bruna\"}, \"descricao\": \"Descrição Teste\", \"pareceres\": null, \"status\": \"FECHADO\", \"titulo\": \"Processo 002\", \"users\": null}";
		
		this.mockMvc.perform(MockMvcRequestBuilders.put("/api/processo/{id}", "1")
					.contentType(MediaType.APPLICATION_JSON)
					.content(data)
					.accept(MediaType.APPLICATION_JSON))
					.andExpect(MockMvcResultMatchers.status().isOk());
	}
	
	@Test
	public void testDELETEProcesso() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.delete("/api/processo/{id}", "1"))
					.andExpect(MockMvcResultMatchers.status().isNoContent());
	}
	
}