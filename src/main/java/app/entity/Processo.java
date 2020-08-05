package app.entity;

import lombok.Getter;
import lombok.Setter;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import java.util.Date;

import javax.persistence.*;
import javax.validation.constraints.Size;

import java.util.HashSet;
import java.util.Set;

@Entity(name="processo")
@Getter @Setter
public class Processo {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private Long iduser;

	private String titulo;

	private String descricao;
	
	private String status;
	
	private Date data = new Date(0);

	public Processo() {
	}

	public Processo(Long iduser, String titulo, String descricao, String status, Date data) {
		super();
		this.iduser = iduser;
		this.titulo = titulo;
		this.descricao = descricao;
		this.status = status;
		this.data = data;
	}

	@OneToMany(mappedBy = "processo", cascade = CascadeType.PERSIST)
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Parecer> parecer = new HashSet<>();

	@ManyToOne
    private User user;
}
