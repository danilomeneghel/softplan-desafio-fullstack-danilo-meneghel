package app.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.EqualsAndHashCode;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;

import java.util.List;

import app.entity.Processo;

@Entity
@Getter @Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class UserDTO {

	@Id
	@EqualsAndHashCode.Include
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String name;

	private String username;

	private String role;

	public UserDTO() {}
	
	public UserDTO(Long id) {
		this.id = id;
	}
	
	public UserDTO(Long id, String username) {
		this.id = id;
		this.username = username;
	}
	
	public UserDTO(Long id, String name, String username, String role) {
		this.id = id;
		this.name = name;
		this.username = username;
		this.role = role;
	}

	@OneToMany(mappedBy = "criador")
	@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
	@JsonIgnore
    private List<Processo> processos;
}
