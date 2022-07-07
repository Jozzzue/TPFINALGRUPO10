package ar.edu.unju.edm.model;





import java.time.LocalDate;

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


import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Component;

@Component
@Entity
@Table(name="tickets")
public class MovieUserTicket {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="ticket_id")
	private Integer id;

	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name = "movie_id")
    private Movie movie;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name = "user_id")
    private Uzer user;
	
	
	@DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate saledate;
	
	//@NotNull
	//@Max(value=3, message="Maximo puede comprar 3 tickets")
	//@Min(value=1, message="El minimo para comprar es 1 ticket")
	private Integer tickets;

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
		return tickets;
	}

	public void setTickets(Integer tickets) {
		this.tickets = tickets;
	}
	
	
	
	
}
