package ar.edu.unju.edm.model;







import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;


import javax.persistence.OneToOne;
import javax.persistence.Table;
import org.springframework.stereotype.Component;

@Component
@Entity
@Table(name="ratings_comments")
public class RatingComment {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="rating_comment_id")
	private Integer id;

	@OneToOne(fetch=FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinColumn(name = "movie_id")
    private Movie movie;
	
	@OneToOne(fetch=FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinColumn(name = "user_id")
    private Uzer user;
	
	//fecha y hora de comentario 
	private String commentDate;
	 
	//contenido del comentario
	private String commentContent;
	
	//valoracion
	private Integer rating;

	
	public RatingComment() {
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
