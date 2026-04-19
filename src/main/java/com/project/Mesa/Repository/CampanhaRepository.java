package com.project.Mesa.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.project.Mesa.Model.Campanha;
import com.project.Mesa.Model.Filial;

@Repository
public interface CampanhaRepository extends JpaRepository<Campanha, Long>{
	boolean existsByPeriodoAndGrupoAndCargoParticipanteAndNomeParticipanteAndEmpresaAndPaginas(String periodo, String grupo,String cargoParticipante,String nomeParticipante, Filial empresa,String paginas);
        /*Query para central*/	
		@Query("SELECT c FROM campanha c JOIN c.empresa f WHERE c.paginas = 'Comida Premiada'")
	    List<Campanha> findCampanhasByPaginaComidaPremiada();
	    
	    @Query("SELECT c FROM campanha c JOIN c.empresa f WHERE c.paginas = 'Melhores Empresas'")
	    List<Campanha> findCampanhasByPaginaMelhoresEmpresas();
	
	    @Query("SELECT c FROM campanha c JOIN c.empresa f WHERE c.paginas = 'Come Melhor'")
	    List<Campanha> findCampanhasByPaginaComeMelhor();
	    
	    @Query("SELECT c FROM campanha c JOIN c.empresa f WHERE c.paginas = 'Destaque'")
	    List<Campanha> findCampanhasByPaginaDestaque();
	    
	    @Query("SELECT c FROM campanha c JOIN c.empresa f WHERE c.paginas = 'Destaque Corporativo'")
	    List<Campanha> findCampanhasByPaginaDestaqueCorporativo();
	    
	    /*Query para Filiais */
	    @Query("SELECT c FROM campanha c JOIN c.empresa f WHERE c.paginas = 'Comida Premiada' AND f.cnpj = :cnpj")
	    List<Campanha> findCampanhasByPaginaComidaPremiadaAndCnpj(String cnpj);
	    
	    @Query("SELECT c FROM campanha c JOIN c.empresa f WHERE c.paginas = 'Melhores Empresas' AND f.cnpj = :cnpj")
	    List<Campanha> findCampanhasByPaginaMelhoresEmpresasAndCnpj(String cnpj);
	    @Query("SELECT c FROM campanha c JOIN c.empresa f WHERE c.paginas = 'Come Melhor' AND f.cnpj = :cnpj")
	    List<Campanha> findCampanhasByPaginaComeMelhorAndCnpj(String cnpj);
	    @Query("SELECT c FROM campanha c JOIN c.empresa f WHERE c.paginas = 'Destaque' AND f.cnpj = :cnpj")
	    List<Campanha> findCampanhasByPaginaDestaqueAndCnpj(String cnpj);
	    @Query("SELECT c FROM campanha c JOIN c.empresa f WHERE c.paginas = 'Destaque Corporativo' AND f.cnpj = :cnpj")
	    List<Campanha> findCampanhasByPaginaDestaqueCorporativoAndCnpj(String cnpj);
	    
	    /*Query para Carla, pois ela pode acessar dados de 2 empresas*/
	    @Query("SELECT c FROM campanha c JOIN c.empresa f WHERE c.paginas = 'Comida Premiada' AND f.cnpj IN (:cnpj, '555555555.0001-55')")
	    List<Campanha> findCampanhasByPaginaComidaPremiadaAndCnpjAndEssencia(String cnpj);
	    
	    @Query("SELECT c FROM campanha c JOIN c.empresa f WHERE c.paginas = 'Melhores Empresas' AND f.cnpj IN (:cnpj, '555555555.0001-55')")
	    List<Campanha> findCampanhasByPaginaMelhoresEmpresasAndCnpjAndEssencia(String cnpj);
	    @Query("SELECT c FROM campanha c JOIN c.empresa f WHERE c.paginas = 'Come Melhor' AND f.cnpj IN (:cnpj, '555555555.0001-55')")
	    List<Campanha> findCampanhasByPaginaComeMelhorAndCnpjAndEssencia(String cnpj);
	    @Query("SELECT c FROM campanha c JOIN c.empresa f WHERE c.paginas = 'Destaque' AND f.cnpj IN (:cnpj, '555555555.0001-55')")
	    List<Campanha> findCampanhasByPaginaDestaqueAndCnpjAndEssencia(String cnpj);
	    @Query("SELECT c FROM campanha c JOIN c.empresa f WHERE c.paginas = 'Destaque Corporativo' AND f.cnpj IN (:cnpj, '555555555.0001-55')")
	    List<Campanha> findCampanhasByPaginaDestaqueCorporativoAndCnpjAndEssencia(String cnpj);
	    
}
