package app.entity;

import lombok.Getter;
import lombok.Setter;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.Date;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "user")
@Getter @Setter
public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "iduser", unique = true, nullable = false)
	private Long id;

	@Size(min = 3, max = 100)
	private String name;

	@Size(min = 3, max = 100)
	private String email;

	@Size(min = 3, max = 50)
	@Column(unique = true)
	private String username;

	@JsonIgnore
	@Size(min = 4, max = 80)
	private String password;

	@JsonIgnore
	@Transient
	@Size(min = 4, max = 80)
	private String passwordCheck;

	private String role;

	private String status;

	@CreationTimestamp
	@Temporal(TemporalType.TIMESTAMP)
	private Date createdAt;

	public User() {}
	
	public User(String username, String password, String role) {
		super();
		this.username = username;
		this.password = password;
		this.role = role;
	}

}
