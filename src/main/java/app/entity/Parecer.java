package app.entity;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

import javax.persistence.*;

import java.util.HashSet;
import java.util.Set;

@Entity(name="parecer")
@Getter @Setter
public class Parecer {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private Long idprocesso;

	private Long iduser;

	private String comentario;

	private String status;

	private Date data = new Date(0);

	public Parecer() {
	}

	public Parecer(Long idprocesso, Long iduser, String comentario, String status, Date data) {
		super();
		this.idprocesso = idprocesso;
		this.iduser = iduser;
		this.comentario = comentario;
		this.status = status;
		this.data = data;
	}

	@ManyToOne(cascade = {CascadeType.ALL})
	private Processo processo;
	
	@ManyToOne
    private User user;
}
