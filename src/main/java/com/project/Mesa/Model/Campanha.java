package com.project.Mesa.Model;

import java.io.Serializable;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.Setter;

@Entity
@Setter
@Getter
public class Campanha implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;
	private String periodo;
	private String grupo;
	@ManyToOne
	@JoinColumn(name = "cnpj_filial", referencedColumnName = "cnpj")
	private Filial empresa;
	@Column(nullable = true)
	private String categoria_participante;
	private String cargoParticipante;
	private String nomeParticipante;
	
	private String meta;
	
	private String real_volume;
	
	private String realizado;
	
	private String meta_atingida;

	private String meta_positivacao;
	
	private String real_positivacao;
	
	private String meta_positivacao_atingida;

	private String valor_por_bateria;
	
	private String faixa_de_premiacao;
	private String valor_premiacao;
	private String valor_com_taxa;
	
	private String colocacao;

	private String positivou;
	
	private String pagina;

	


}
