package app.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.EqualsAndHashCode;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;

import java.util.HashSet;
import java.util.Set;

import app.entity.Processo;
import app.entity.Parecer;

@Entity(name="user")
@Getter @Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class UserDTO {

	@Id
	@EqualsAndHashCode.Include
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String name;

	private String username;

	private String status;

	public UserDTO() {}
	
	public UserDTO(Long id) {
		this.id = id;
	}
	
	public UserDTO(Long id, String username) {
		this.id = id;
		this.username = username;
	}

	@OneToMany(mappedBy = "criador")
	@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
	@JsonIgnore
    private Set<Processo> processos = new HashSet<>();

    @OneToMany(mappedBy = "user")
	@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
	@JsonIgnore
    private Set<Parecer> pareceres = new HashSet<>();
}
