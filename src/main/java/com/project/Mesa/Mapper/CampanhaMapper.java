package com.project.Mesa.Mapper;

import com.project.Mesa.Controller.dto.campanha.CampanhaRequestDTO;
import com.project.Mesa.Controller.dto.campanha.CampanhaResponseDTO;
import com.project.Mesa.Model.Campanha;

public interface CampanhaMapper {
	Campanha toCampanha(CampanhaRequestDTO campanhaSheetDTO);
	
	CampanhaResponseDTO campanhaResponseDTO(Campanha campanha);
}
