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
import app.entity.Processo;
import app.controller.ProcessoController;
import app.service.ProcessoServiceImpl;

public class ProcessoControllerTest extends ApplicationTests {

	private MockMvc mockMvc;
	
	@Autowired
	private ProcessoController processoController;

	@Autowired
	private ProcessoServiceImpl processoService;
	
	@Before
	public void setUp() {
		this.mockMvc = MockMvcBuilders.standaloneSetup(processoController).build();
	}
	
	@Test
	public void testPOSTProcesso() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.post("/api/processo")
				.param("titulo", "Processo Teste 2")
				.param("descricao", "Descrição Teste.")
				.param("idcriador", "1")
				.param("createdAt", "2020-08-04 23:25:10"));
	}
	
	@Test
	public void testGETProcessos() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.get("/api/processo"))
			.andExpect(MockMvcResultMatchers.status().isOk());
	}
	
	@Test
	public void testGETProcesso() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.get("/api/processo/" + 1))
		.andExpect(MockMvcResultMatchers.status().isOk());
	}
	
	@Test
	public void testPUTProcesso() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.post("/api/processo")
					.param("titulo", "Processo Teste 3")
					.param("descricao", "Descrição Teste.")
					.param("idcriador", "1")
					.param("createdAt", "2020-08-04 23:25:10"));
		this.mockMvc.perform(MockMvcRequestBuilders.put("/api/processo/" + 1));
	}
	
	@Test
	public void testDELETEProcesso() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.delete("/api/processo/" + 1));
	}
		
}