package app.entity;

import lombok.Getter;
import lombok.Setter;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import java.util.Date;

import javax.persistence.*;

import app.dto.UserDTO;

@Entity
@Table(name = "parecer")
@Getter @Setter
public class Parecer {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "idparecer", unique = true, nullable = false)
	private Long id;

	private String comentario;

	@CreationTimestamp
	@Temporal(TemporalType.TIMESTAMP)
	private Date createdAt;

	@UpdateTimestamp
	@Temporal(TemporalType.TIMESTAMP)
	private Date updatedAt;

	public Parecer() {}

	@ManyToOne(cascade = CascadeType.MERGE)
	@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
	@JoinColumn(name = "idprocesso", referencedColumnName = "idprocesso")
	@JsonBackReference
	private Processo processo;
	
	@ManyToOne(cascade = CascadeType.MERGE)
	@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
	@JoinColumn(name = "iduser", referencedColumnName = "iduser")
	private UserDTO user;
	
}
