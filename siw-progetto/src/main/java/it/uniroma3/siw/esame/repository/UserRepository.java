package it.uniroma3.siw.esame.repository;

import org.springframework.data.repository.CrudRepository;

import it.uniroma3.siw.esame.model.User;

public interface UserRepository extends CrudRepository<User, Long> {

}