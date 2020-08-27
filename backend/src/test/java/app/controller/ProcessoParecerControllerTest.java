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
import app.controller.ProcessoParecerController;

public class ProcessoParecerControllerTest extends ApplicationTests {

	private MockMvc mockMvc;
	
	@Autowired
	private ProcessoParecerController processoParecerController;
	
	@Before
	public void setUp() {
		this.mockMvc = MockMvcBuilders.standaloneSetup(processoParecerController).build();
	}
	
	@Test
	public void testGETPareceres() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.get("/api/processo-parecer")
					.contentType(MediaType.APPLICATION_JSON)
					.accept(MediaType.APPLICATION_JSON))
					.andExpect(MockMvcResultMatchers.status().isOk());
	}
	
	@Test
	public void testPOSTParecer() throws Exception {
		String data = "{\"id\": 1, \"titulo\": \"Processo 001\", \"descricao\": \"Descrição Processo\", \"criador\": {\"id\": 2, \"name\": \"Bruna\", \"role\": \"TRIAD\", \"username\": \"bruna\"}, \"pareceres\": [{\"comentario\": \"Comentário Teste\", \"user\": {\"id\": 3, \"name\": \"Carlos\", \"role\": \"FINAL\", \"username\": \"carlos\"}}], \"status\": \"ABERTO\", \"users\": [{\"id\": 3, \"name\": \"Carlos\", \"role\": \"FINAL\", \"username\": \"carlos\"}]}";
		
		this.mockMvc.perform(MockMvcRequestBuilders.post("/api/processo-parecer")
				.contentType(MediaType.APPLICATION_JSON)
				.content(data)
				.accept(MediaType.APPLICATION_JSON))
				.andExpect(MockMvcResultMatchers.status().isCreated());
	}
	
	@Test
	public void testPUTParecer() throws Exception {
		String data = "{\"id\": 1, \"titulo\": \"Processo 001\", \"descricao\": \"Descrição Processo\", \"criador\": {\"id\": 2, \"name\": \"Bruna\", \"role\": \"TRIAD\", \"username\": \"bruna\"}, \"pareceres\": [{\"comentario\": \"Comentário Teste 2\", \"user\": {\"id\": 4, \"name\": \"Cátia\", \"role\": \"FINAL\", \"username\": \"catia\"}}], \"status\": \"FECHADO\", \"users\": [{\"id\": 4, \"name\": \"Cátia\", \"role\": \"FINAL\", \"username\": \"catia\"}]}";
		
		this.mockMvc.perform(MockMvcRequestBuilders.put("/api/processo-parecer/{id}", "1")
					.contentType(MediaType.APPLICATION_JSON)
					.content(data)
					.accept(MediaType.APPLICATION_JSON))
					.andExpect(MockMvcResultMatchers.status().isOk());
	}
		
}