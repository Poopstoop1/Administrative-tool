package com.project.Mesa.Controller.dto.campanha;

import com.project.Mesa.Model.Campanha;
import com.project.Mesa.Model.Filial;

public record CampanhaResponseDTO(Long id, String periodo, Filial empresa, String categoria_participante,
		String cargoParticipante, String nomeParticipante, String meta, String real_volume, String realizado,
		String meta_atingida, String meta_positivacao, String real_positivacao, String meta_positivacao_atingida,
		String valor_por_bateria, String faixa_de_premiacao, String valor_premiacao, String valor_com_taxa,
		String colocacao, String positivou, String paginas) {

	public CampanhaResponseDTO fromCampanhaResponseDTO(Campanha campanha) {
		return new CampanhaResponseDTO(campanha.getId(), campanha.getPeriodo(), campanha.getEmpresa(),
				campanha.getCategoria_participante(), campanha.getCargoParticipante(), campanha.getNomeParticipante(),
				campanha.getMeta(), campanha.getReal_volume(), campanha.getRealizado(), campanha.getMeta_atingida(),
				campanha.getMeta_positivacao(), campanha.getReal_positivacao(), campanha.getMeta_positivacao_atingida(),
				campanha.getValor_por_bateria(), campanha.getFaixa_de_premiacao(), campanha.getValor_premiacao(),
				campanha.getValor_com_taxa(), campanha.getColocacao(), campanha.getPositivou(), campanha.getPaginas());
	}

}
