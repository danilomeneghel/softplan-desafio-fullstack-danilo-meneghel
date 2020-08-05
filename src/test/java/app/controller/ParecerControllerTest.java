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
	
	//Cria um novo objeto
	private Parecer parecer = new Parecer(Long.valueOf("1"), Long.valueOf("1"), "Parecer Teste", "Ativo", new Date(0));
	
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
				.param("idprocesso", "1")
				.param("iduser", "1")
				.param("comentario", "Parecer Teste 2")
				.param("status", "Ativo")
				.param("data", "2019-12-16 06:25:10"));
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
		//Edita alguns valores
		parecer.setComentario("Parecer de Teste");
		parecer.setStatus("Inativo");
		this.mockMvc.perform(MockMvcRequestBuilders.put("/api/parecer/" + parecer.getId()));
	}
	
	@Test
	public void testDELETEParecer() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.delete("/api/parecer/" + parecer.getId()));
	}
	
	@Test
	public void testIsParecerExist() throws Exception {
		parecerService.isParecerExist(parecer);
	}
	
}