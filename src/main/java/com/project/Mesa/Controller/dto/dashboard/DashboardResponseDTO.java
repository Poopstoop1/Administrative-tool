package com.project.Mesa.Controller.dto.dashboard;

import java.util.List;

import com.project.Mesa.Controller.dto.campanha.CampanhaResponseDTO;

public record DashboardReponseDTO( List<CampanhaResponseDTO> campanhas,
		List<CampanhaResponseDTO> campanhas1, List<CampanhaResponseDTO> campanhas2,
		List<CampanhaResponseDTO> campanhas3, List<CampanhaResponseDTO> campanhas4) {

	public DashboardReponseDTO FromDashBoardResponseDTO( List<CampanhaResponseDTO> campanhas,
			List<CampanhaResponseDTO> campanhas1, List<CampanhaResponseDTO> campanhas2,
			List<CampanhaResponseDTO> campanhas3, List<CampanhaResponseDTO> campanhas4) {
		return new DashboardReponseDTO(campanhas, campanhas1, campanhas2, campanhas3, campanhas4);
	}
}
