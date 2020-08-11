package app.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.EqualsAndHashCode;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import java.util.Date;

import javax.persistence.*;
import javax.validation.constraints.Size;

import java.util.List;

import app.dto.UserDTO;

@Entity(name="processo")
@Getter @Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@JsonIdentityInfo(generator= ObjectIdGenerators.PropertyGenerator.class, property="id")
public class Processo {

	@Id
	@EqualsAndHashCode.Include
	@GeneratedValue(strategy = GenerationType.IDENTITY)
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
	@JoinColumn(name = "idcriador", referencedColumnName="id")
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
