package com.project.Mesa.Repository;


import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;


import com.project.Mesa.Model.Users;

import jakarta.transaction.Transactional;

@Repository
@Transactional
public interface UserRepository extends CrudRepository<Users, Long>{

	@Query("select u from Users u where u.login = ?1 ")
	Optional<Users> findByUsername(String login);

}
