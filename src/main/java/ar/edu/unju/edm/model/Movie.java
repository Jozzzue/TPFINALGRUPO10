package ar.edu.unju.edm.model;





import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.validation.constraints.FutureOrPresent;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Component;

@Component
@Entity
public class Movie {

	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Id
	@Column(name="movie_id")
	private int id;
	
	@Size(min=2, max=50, message="EL nombre debe tener 2 caracteres minimo, maximo 50")
	@NotBlank(message="El nombre no puede ser espacios en blanco")
	@NotEmpty(message="El nombre no puede estar vacio")
	private String name;
	
	@Size(min=0, max=200, message="La descripcion debe tener maximo 100 caracteres")
	@NotBlank(message="La descripcion no puede ser espacios en blanco")
	private String description;
	
	@NotNull(message="Debe elegir al menos un genero")
	private String genres;
	
	@DateTimeFormat(pattern="yyyy-MM-dd")
	@FutureOrPresent(message="El estreno todavia no ocurrio")
	private LocalDate premiere;
	
	
	@NotNull(message="Ingrese horario de estreno")
	private String schedule;
	
	
	@NotNull(message="Ingrese duracion de la pelicula")
	private String lenght;
	
	
	@Lob
	private String image;
	private Boolean status;
	
	@Min(value=1, message="Elija una sala")
	@Max(value=7, message="Elija una sala")
	@NotNull(message="Elija una sala")
	private int hall;
	
	public Movie() {
		// TODO Auto-generated constructor stub
	}
	public int getId() {
		return id;
	}


	public void setId(int id) {
		this.id = id;
	}


	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}


	public String getDescription() {
		return description;
	}


	public void setDescription(String description) {
		this.description = description;
	}


	public Boolean getStatus() {
		return status;
	}


	public void setStatus(Boolean status) {
		this.status = status;
	}
	public String getImage() {
		return image;
	}
	public void setImage(String image) {
		this.image = image;
	}
	
	public String getGenres() {
		return genres;
	}
	public void setGenres(String genres) {
		this.genres = genres;
	}
	public LocalDate getPremiere() {
		return premiere;
	}
	public void setPremiere(LocalDate premiere) {
		this.premiere = premiere;
	}
	public String getSchedule() {
		return schedule;
	}
	public void setSchedule(String schedule) {
		this.schedule = schedule;
	}
	
	public int getHall() {
		return hall;
	}
	public void setHall(int hall) {
		this.hall = hall;
	}
	public String getLenght() {
		return lenght;
	}
	public void setLenght(String lenght) {
		this.lenght = lenght;
	}
}
