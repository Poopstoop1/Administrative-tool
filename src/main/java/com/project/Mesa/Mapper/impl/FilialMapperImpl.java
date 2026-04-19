package com.project.Mesa.Mapper.impl;

import org.springframework.stereotype.Component;

import com.project.Mesa.Controller.dto.filial.FilialRequestDTO;
import com.project.Mesa.Controller.dto.filial.FilialResponseDTO;
import com.project.Mesa.Mapper.FilialMapper;
import com.project.Mesa.Model.Filial;

@Component
public class FilialMapperImpl implements FilialMapper {

	@Override
	public Filial toFilial(FilialRequestDTO filialRequestDTO) {
		Filial filial = new Filial();
		filial.setCnpj(filialRequestDTO.cnpj().replaceAll("\\D", ""));
		filial.setNome(filialRequestDTO.nome());
		filial.setRazaosocial(filialRequestDTO.razaosocial());
		return filial;
	}

	@Override
	public FilialResponseDTO toFilialResponseDTO(Filial filial) {
		return FilialResponseDTO.fromFilialResponseDTO(filial);
	}

	@Override
	public void atualizarCampos(Filial filial, FilialRequestDTO filialRequestDTO) {
		if (filialRequestDTO.cnpj() != null && filialRequestDTO.cnpj().isBlank())
			filial.setCnpj(filialRequestDTO.cnpj().replaceAll("\\D", " "));
		if (filialRequestDTO.nome() != null && filialRequestDTO.nome().isBlank())
			filial.setNome(filialRequestDTO.nome());
		if (filialRequestDTO.razaosocial() != null && filialRequestDTO.razaosocial().isBlank())
			filial.setRazaosocial(filialRequestDTO.razaosocial());
	}

}
