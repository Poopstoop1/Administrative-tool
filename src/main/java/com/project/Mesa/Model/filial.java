package com.project.Mesa.Model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;

import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Filial implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Id
	@Column(unique = true)
	private String cnpj;
	private String nome;
	private String razaoSocial;
	
	@OneToMany(mappedBy = "empresa",orphanRemoval = true, cascade = CascadeType.ALL)
	private List<Usuario> usuarios = new ArrayList<Usuario>();
	
	@OneToMany(mappedBy = "empresa",orphanRemoval = true, cascade = CascadeType.ALL)
	private List<Campanha> Campanha = new ArrayList<Campanha>();
		
}
