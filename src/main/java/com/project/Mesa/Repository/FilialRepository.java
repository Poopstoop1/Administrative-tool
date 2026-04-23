package com.project.Mesa.Repository;


import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.stereotype.Repository;

import com.project.Mesa.Model.Filial;


@Repository
public interface FilialRepository extends JpaRepository<Filial, String>{
	Optional<Filial> findByNome(String nome);
	
	Boolean existsByNome(String nome);
	
	Boolean existsByRazaoSocial(String razaoSocial);
}
