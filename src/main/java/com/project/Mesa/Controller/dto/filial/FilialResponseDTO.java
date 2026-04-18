package com.project.Mesa.Controller.dto.filial;

import com.project.Mesa.Model.Filial;

public record FilialResponseDTO(String cnpj, String nome, String razaosocial) {
	public static FilialResponseDTO fromFilialResponseDTO(Filial Filial) {
		return new FilialResponseDTO(Filial.getCnpj(), Filial.getNome(), Filial.getRazaosocial());
	}
}
