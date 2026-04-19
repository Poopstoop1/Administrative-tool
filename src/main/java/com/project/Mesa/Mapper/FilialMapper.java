package com.project.Mesa.Mapper;

import com.project.Mesa.Controller.dto.filial.FilialRequestDTO;
import com.project.Mesa.Controller.dto.filial.FilialResponseDTO;
import com.project.Mesa.Model.Filial;

public interface FilialMapper {
	Filial toFilial(FilialRequestDTO filialRequestDTO);
	FilialResponseDTO toFilialResponseDTO(Filial usuario);
	void atualizarCampos(Filial Filial, FilialRequestDTO filialRequestDTO);
}
