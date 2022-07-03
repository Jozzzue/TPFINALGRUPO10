package ar.edu.unju.edm.model;



import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.springframework.stereotype.Component;



@Component
@Entity
@Table(name="users")
public class Uzer {
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Id
	@Column(name="user_id")
	private int id;

	
	@NotEmpty //@Email
	private String email;
	
	
	//@Size(min=4, max=30, message="La contraseña debe tener 4 caracteres minimo, maximo 30")
	//@NotBlank(message="La contraseña no puede ser espacios en blanco")
	private String password;
	
	
	@Size(min=2, max=50, message="El apellido debe tener 2 caracteres minimo, maximo 50")
	@NotBlank(message="El apellido no puede ser espacios en blanco")
	@NotEmpty(message="El apellido no puede estar vacio")
	private String lastname;
	
	
	@Size(min=2, max=50, message="EL nombre debe tener 2 caracteres minimo, maximo 50")
	@NotBlank(message="El nombre no puede ser espacios en blanco")
	@NotEmpty(message="El nombre no puede estar vacio")
	private String name;
	
	
	@Min(value=1000000, message="El dni debe ser mayor a 1.000.000")
	@Max(value=99999999, message="El dni debe ser menor a 99.999.999")
	private int dni;
	
	@NotNull(message="Seleccione un tipo")
	private String type;
	
	private Boolean status;
	

	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public Boolean getStatus() {
		return status;
	}
	public void setStatus(Boolean status) {
		this.status = status;
	}
	public int getDni() {
		return dni;
	}
	public void setDni(int dni) {
		this.dni = dni;
	}
	public Uzer() {
		// TODO Auto-generated constructor stub
	}
	public Uzer(String email, String password) {
		super();
		this.email = email;
		this.password = password;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getLastname() {
		return lastname;
	}
	public void setLastname(String lastname) {
		this.lastname = lastname;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}

	
}
