package app.entity;

import lombok.Getter;
import lombok.Setter;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
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

	private String titulo;

	private String descricao;
	
	private String status;
	
	@CreationTimestamp
	@Temporal(TemporalType.TIMESTAMP)
	private Date createdAt;
	
	@UpdateTimestamp
	@Temporal(TemporalType.TIMESTAMP)
	private Date updatedAt;

	public Processo() {
	}

	@OneToMany(mappedBy = "processo", cascade = CascadeType.PERSIST)
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Parecer> parecer = new HashSet<>();

	@ManyToOne
	@JoinColumn(name = "iduser", referencedColumnName="id")
    private User user;
}
