package com.project.Mesa.Model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;

import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;

@Entity
public class filial implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Id
	private String cnpj;
	private String nome;
	private String razaosocial;
	
	@OneToMany(mappedBy = "empresa",orphanRemoval = true, cascade = CascadeType.ALL)
	private List<Users> usuarios = new ArrayList<Users>();
	
	@OneToMany(mappedBy = "empresa",orphanRemoval = true, cascade = CascadeType.ALL)
	private List<campanhas> campanhas = new ArrayList<campanhas>();
	
	public String getCnpj() {
		return cnpj;
	}
	public void setCnpj(String cnpj) {
		this.cnpj = cnpj;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public String getRazaosocial() {
		return razaosocial;
	}
	public void setRazaosocial(String razaosocial) {
		this.razaosocial = razaosocial;
	}
	
	
}
