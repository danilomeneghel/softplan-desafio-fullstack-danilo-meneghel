package app.entity;

import lombok.Getter;
import lombok.Setter;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import java.util.Date;

import javax.persistence.*;
import javax.validation.constraints.Size;

import java.util.List;

import app.dto.UserDTO;

@Entity
@Table(name = "processo")
@Getter @Setter
public class Processo {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "idprocesso", unique = true, nullable = false)
	private Long id;

	private String titulo;

	private String descricao;

	private String status = "ABERTO";

	@CreationTimestamp
	@Temporal(TemporalType.TIMESTAMP)
	private Date createdAt;
	
	@UpdateTimestamp
	@Temporal(TemporalType.TIMESTAMP)
	private Date updatedAt;

	public Processo() {}

	@ManyToOne
	@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
	@JoinColumn(name = "idcriador", referencedColumnName = "iduser")
	private UserDTO criador;

	@OneToMany(mappedBy = "processo", cascade = CascadeType.ALL)
	@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
	@JsonManagedReference
    private List<Parecer> pareceres;
	
	@ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
        name = "processo_user",
        joinColumns = @JoinColumn(name = "idprocesso"),
        inverseJoinColumns = @JoinColumn(name = "iduser")
	)
	private List<UserDTO> users;
}
