package ar.edu.unju.edm.model;





import java.time.LocalDate;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;

import javax.persistence.ManyToOne;
import javax.persistence.Table;
//import javax.validation.constraints.Max;
//import javax.validation.constraints.Min;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Component;

@Component
@Entity
@Table(name="movie_user")
public class MovieUser {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="movie_user_id")
	private Integer id;

	@ManyToOne(fetch=FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinColumn(name = "movie_id")
    private Movie movie;
	
	@ManyToOne(fetch=FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinColumn(name = "user_id")
    private Uzer user;
	
	
	@DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate saledate;
	
	
	//cantidad de tickets del usuario
	@Min(value=1, message="El minimo de tickets es 1")
	@Max(value=3, message="El maximo de tickets es 3")
	private Integer tickets;
	

	
	public MovieUser() {
		// TODO Auto-generated constructor stub
	}
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Movie getMovie() {
		return movie;
	}

	public void setMovie(Movie movie) {
		this.movie = movie;
	}

	public Uzer getUser() {
		return user;
	}

	public void setUser(Uzer user) {
		this.user = user;
	}

	public LocalDate getSaledate() {
		return saledate;
	}

	public void setSaledate(LocalDate saledate) {
		this.saledate = saledate;
	}

	public Integer getTickets() {
		if(tickets==null)
			return 0;
		else
		return tickets;
	}

	public void setTickets(Integer tickets) {
		this.tickets = tickets;
	}

	
	
	
	
}
