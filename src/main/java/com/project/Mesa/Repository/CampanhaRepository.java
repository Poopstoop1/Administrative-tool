package com.project.Mesa.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.project.Mesa.Model.Campanha;
import com.project.Mesa.Model.Filial;

@Repository
public interface CampanhaRepository extends JpaRepository<Campanha, Long> {
	boolean existsByPeriodoAndGrupoAndCargoParticipanteAndNomeParticipanteAndEmpresaAndPagina(String periodo,
			String grupo, String cargoParticipante, String nomeParticipante, Filial empresa, String pagina);

	List<Campanha> findByPagina(String pagina);

	List<Campanha> findByPaginaAndEmpresaCnpj(String pagina, String cnpj);

	List<Campanha> findByPaginaAndEmpresaCnpjIn(String pagina, List<String> cnpjs);

}
