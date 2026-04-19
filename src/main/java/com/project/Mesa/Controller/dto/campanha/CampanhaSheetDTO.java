package com.project.Mesa.Controller.dto.campanha;

import com.project.Mesa.Model.Filial;

public record CampanhaSheetDTO(String periodo,String grupo, Filial empresa, String categoria_participante,
		String cargoParticipante, String nomeParticipante, String meta, String real_volume, String realizado,
		String meta_atingida, String meta_positivacao, String real_positivacao, String meta_positivacao_atingida,
		String valor_por_bateria, String faixa_de_premiacao, String valor_premiacao, String valor_com_taxa,
		String colocacao, String positivou, String pagina) {



}
