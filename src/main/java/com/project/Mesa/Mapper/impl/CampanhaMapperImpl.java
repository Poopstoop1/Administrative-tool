package com.project.Mesa.Mapper.impl;

import org.springframework.stereotype.Component;

import com.project.Mesa.Controller.dto.campanha.CampanhaRequestDTO;
import com.project.Mesa.Controller.dto.campanha.CampanhaResponseDTO;
import com.project.Mesa.Mapper.CampanhaMapper;
import com.project.Mesa.Model.Campanha;

@Component
public class CampanhaMapperImpl implements CampanhaMapper {

	@Override
	public Campanha toCampanha(CampanhaRequestDTO campanhaSheetDTO) {
		Campanha campanha = new Campanha();
		campanha.setPeriodo(campanhaSheetDTO.periodo());
		campanha.setGrupo(campanhaSheetDTO.grupo());
		campanha.setCategoria_participante(campanhaSheetDTO.categoria_participante());
		campanha.setCargoParticipante(campanhaSheetDTO.cargoParticipante());
		campanha.setNomeParticipante(campanhaSheetDTO.nomeParticipante());
		campanha.setMeta(campanhaSheetDTO.meta());
		campanha.setRealizado(campanhaSheetDTO.realizado());
		campanha.setMeta_atingida(campanhaSheetDTO.meta_atingida());
		campanha.setValor_por_bateria(campanhaSheetDTO.valor_por_bateria());
		campanha.setValor_premiacao(campanhaSheetDTO.valor_premiacao());
		campanha.setValor_com_taxa(campanhaSheetDTO.valor_com_taxa());
		campanha.setColocacao(campanhaSheetDTO.colocacao());
		campanha.setPositivou(campanhaSheetDTO.positivou());
		campanha.setPagina(campanhaSheetDTO.pagina());
		return campanha;
	}

	@Override
	public CampanhaResponseDTO campanhaResponseDTO(Campanha campanha) {
		return CampanhaResponseDTO.fromCampanhaResponseDTO(campanha);
	}

}
