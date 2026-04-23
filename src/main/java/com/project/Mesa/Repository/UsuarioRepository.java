package com.project.Mesa.Repository;


import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.project.Mesa.Model.Usuario;


@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long>{

	@Query("select u from Usuario u where u.login = ?1 ")
	Optional<Usuario> findByUsername(String login);
	
	Boolean existsByLogin(String login);
	
}
