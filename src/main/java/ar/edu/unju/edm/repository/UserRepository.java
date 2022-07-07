package ar.edu.unju.edm.repository;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import ar.edu.unju.edm.model.Uzer;

@Repository
public interface  UserRepository extends CrudRepository <Uzer,Integer>{

	public Optional<Uzer> findByDni(Integer dni);
}


