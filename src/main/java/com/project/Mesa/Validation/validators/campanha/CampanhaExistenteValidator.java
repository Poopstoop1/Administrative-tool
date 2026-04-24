package com.project.Mesa.Validation.validators.campanha;

import org.springframework.stereotype.Component;
import com.project.Mesa.Model.Campanha;
import com.project.Mesa.Repository.CampanhaRepository;

@Component
public class CampanhaExistenteValidator{
	
	private final CampanhaRepository campanhaRepository;
	
	public CampanhaExistenteValidator(CampanhaRepository campanhaRepository) {
		this.campanhaRepository = campanhaRepository;

	}

	public Boolean buscarCampanhaExistente(Campanha dto) {
		Boolean campanhaExistente = campanhaRepository
				.existsByPeriodoAndGrupoAndCargoParticipanteAndNomeParticipanteAndEmpresaAndPagina(dto.getPeriodo(), 
						dto.getGrupo(), dto.getCargoParticipante(), dto.getNomeParticipante(), dto.getEmpresa(), dto.getPagina());
		
		return campanhaExistente;
		
	}

}
