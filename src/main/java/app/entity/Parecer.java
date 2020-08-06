package app.entity;

import lombok.Getter;
import lombok.Setter;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

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

	private String comentario;

	private String status;

	@CreationTimestamp
	@Temporal(TemporalType.TIMESTAMP)
	private Date createdAt;

	@UpdateTimestamp
	@Temporal(TemporalType.TIMESTAMP)
	private Date updatedAt;

	public Parecer() {
	}

	@ManyToOne(cascade = {CascadeType.ALL})
	@JoinColumn(name = "idprocesso", referencedColumnName="id")
	private Processo processo;
	
	@ManyToOne
	@JoinColumn(name = "iduser", referencedColumnName="id")
    private User user;
}
