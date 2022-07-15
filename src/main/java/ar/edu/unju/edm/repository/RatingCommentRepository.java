package ar.edu.unju.edm.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import ar.edu.unju.edm.model.RatingComment;


@Repository
public interface  RatingCommentRepository extends CrudRepository <RatingComment,Integer>{
	
           
}
