package ar.edu.unju.edm.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import ar.edu.unju.edm.model.MovieUser;


@Repository
public interface  MovieUserRepository extends CrudRepository <MovieUser,Integer>{
	
           
}
