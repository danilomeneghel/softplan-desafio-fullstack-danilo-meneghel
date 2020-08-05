package app.entity;

import lombok.Getter;
import lombok.Setter;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import java.util.Date;

import javax.persistence.*;

import java.util.HashSet;
import java.util.Set;

@Entity(name="user")
@Getter @Setter
public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Size(min = 3, max = 100)
	private String name;

	@Size(min = 3, max = 100)
	private String email;

	@Size(min = 3, max = 50)
	private String username;

	@Size(min = 4, max = 80)
	private String password;

	@Transient
	@Size(min = 4, max = 80)
	private String passwordCheck;

	private String role;

	private String status;

	private Date data = new Date(0);

	public User() {
	}

	public User(String username, String password, String role) {
		super();
		this.username = username;
		this.password = password;
		this.role = role;
	}

	public User(@Size(min = 3, max = 100) String name, @Size(min = 3, max = 100) String email, 
			@Size(min = 3, max = 50) String username, @Size(min = 4, max = 80) String password, String role,
			String status, Date data) {
		super();
		this.name = name;
		this.email = email;
		this.username = username;
		this.password = password;
		this.role = role;
		this.status = status;
		this.data = data;
	}

	@OneToMany(mappedBy = "user")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Processo> processo = new HashSet<>();

    @OneToMany(mappedBy = "user")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Parecer> parecer = new HashSet<>();
}
