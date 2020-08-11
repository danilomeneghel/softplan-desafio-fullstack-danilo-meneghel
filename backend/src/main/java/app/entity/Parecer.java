package app.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.EqualsAndHashCode;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import java.util.Date;

import javax.persistence.*;

import app.dto.UserDTO;

@Entity(name="parecer")
@Getter @Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@JsonIdentityInfo(generator= ObjectIdGenerators.PropertyGenerator.class, property="id")
public class Parecer {

	@Id
	@EqualsAndHashCode.Include
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String comentario;

	@CreationTimestamp
	@Temporal(TemporalType.TIMESTAMP)
	private Date createdAt;

	@UpdateTimestamp
	@Temporal(TemporalType.TIMESTAMP)
	private Date updatedAt;

	public Parecer() {}

	@ManyToOne
	@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
	@JoinColumn(name = "idprocesso")
	@JsonBackReference
	private Processo processo;
	
	@ManyToOne
	@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
	@JoinColumn(name = "iduser")
	private UserDTO user;
	
}
