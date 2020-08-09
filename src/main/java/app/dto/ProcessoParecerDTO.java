package app.dto;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter @Setter
public class ProcessoParecerDTO {

	private Long idprocesso;

	private Long idparecer;
	
	private String comentario;
	
}
