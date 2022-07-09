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
@Table(name="movie_user")
public class MovieUser {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="movie_user_id")
	private Integer id;

	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name = "movie_id")
    private Movie movie;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name = "user_id")
    private Uzer user;
	
	
	@DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate saledate;
	
	
	//cantidad de tickets del usuario
	private Integer tickets;
	
	//fecha y hora de comentario 
	private String commentDate;
	 
	//contenido del comentario
	private String commentContent;
	
	//valoracion
	private Integer rating;

	
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

	public String getCommentDate() {
		return commentDate;
	}

	public void setCommentDate(String commentDate) {
		this.commentDate = commentDate;
	}

	public String getCommentContent() {
		return commentContent;
	}

	public void setCommentContent(String commentContent) {
		this.commentContent = commentContent;
	}

	public Integer getRating() {
		if(rating==null)
			return 0;
		else
		return rating;
	}

	public void setRating(Integer rating) {
		this.rating = rating;
	}
	
	
	
	
}
