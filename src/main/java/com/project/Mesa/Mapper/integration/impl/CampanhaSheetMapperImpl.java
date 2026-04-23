package com.project.Mesa.Mapper.integration.impl;


import java.util.List;

import org.springframework.stereotype.Component;

import com.project.Mesa.Model.Campanha;
import com.project.Mesa.Repository.FilialRepository;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class CampanhaSheetMapperImpl {

	private final FilialRepository filialRepository;

    public Campanha toCampanha(List<Object> row, String sheetName) {
        Campanha campanha = new Campanha();
        campanha.setPagina(sheetName);
        
        // Lógica comum a quase todas as abas
        campanha.setPeriodo(getValue(row, 0));
        campanha.setGrupo(getValue(row, 1));
        
        // Busca de Filial (comum em várias abas)
        String nomeFilial = getValue(row, 2);
        if (nomeFilial != null) {
            filialRepository.findByNome(nomeFilial).ifPresent(campanha::setEmpresa);
        }

        if (sheetName.equals("Comida Premiada")) {
            mapComidaPremiada(campanha, row);
        } else if (sheetName.equals("Melhores Empresas")) {
            mapMelhoresEmpresas(campanha, row);
        } else if (sheetName.equals("Come Melhor")) {
        	mapComeMelhor(campanha, row);
        }
        else if (sheetName.equals("Destaque")) {
        	mapDestaque(campanha, row);
        }
        else if (sheetName.equals("Destaque Corporativo")) {
        	mapDestaqueCorporativo(campanha, row);
        }
        
        return campanha;
    }

    private void mapComidaPremiada(Campanha c, List<Object> row) {
        c.setCategoria_participante(getValue(row,5));
    	c.setCargoParticipante(getValue(row, 6));
        c.setNomeParticipante(getValue(row, 7));
        c.setMeta(getValue(row, 8));
        c.setRealizado(getValue(row, 9));
        c.setMeta_atingida(getValue(row, 10));
        c.setValor_por_bateria(getValue(row, 11));
        c.setValor_premiacao(getValue(row, 12));
        c.setValor_com_taxa(getValue(row, 13));
    }
    
    private void mapMelhoresEmpresas(Campanha c, List<Object> row) {
    	c.setNomeParticipante(getValue(row, 5));
        c.setMeta(getValue(row, 6));
        c.setRealizado(getValue(row, 7));
        c.setMeta_atingida(getValue(row, 8));
        c.setPositivou(getValue(row, 9));
        c.setFaixa_de_premiacao(getValue(row, 10));
        c.setValor_premiacao(getValue(row, 11));
        c.setValor_com_taxa(getValue(row, 12));
        
    }
    private void mapComeMelhor(Campanha c, List<Object> row) {
    	c.setCargoParticipante(getValue(row, 3));
        c.setNomeParticipante(getValue(row, 4));
        c.setMeta(getValue(row, 5));
        c.setReal_volume(getValue(row, 6));
        c.setMeta_atingida(getValue(row, 7));
        c.setMeta_positivacao(getValue(row, 8));
        c.setReal_positivacao(getValue(row, 9));
        c.setMeta_positivacao_atingida(getValue(row, 10));
        c.setValor_premiacao(getValue(row, 11));
        c.setValor_com_taxa(getValue(row, 12));
      
        
    }
    private void mapDestaque(Campanha c, List<Object> row) {
    	c.setCategoria_participante(getValue(row, 3));
        c.setNomeParticipante(getValue(row, 4));
        c.setValor_premiacao(getValue(row, 5));
        c.setValor_com_taxa(getValue(row, 6));
        
    }
    
    private void mapDestaqueCorporativo(Campanha c, List<Object> row) {
    	c.setCategoria_participante(getValue(row, 3));
    	c.setColocacao(getValue(row, 4));
        c.setNomeParticipante(getValue(row, 5));
        c.setValor_premiacao(getValue(row, 6));
        c.setValor_com_taxa(getValue(row, 7));       
    }

    private String getValue(List<Object> row, int index) {
        return (row != null && index < row.size() && row.get(index) != null) 
               ? row.get(index).toString() : null;
    }

}
