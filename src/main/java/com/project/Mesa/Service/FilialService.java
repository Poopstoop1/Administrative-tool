package com.project.Mesa.Service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.project.Mesa.Controller.dto.filial.FilialRequestDTO;
import com.project.Mesa.Controller.dto.filial.FilialResponseDTO;
import com.project.Mesa.Mapper.impl.FilialMapperImpl;
import com.project.Mesa.Model.Filial;
import com.project.Mesa.Repository.FilialRepository;
import com.project.Mesa.Service.exception.ResourceNotFoundException;
import com.project.Mesa.Validation.validators.filial.FilialValidator;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class FilialService {
	private final FilialRepository filialRepository;
	private final FilialMapperImpl filialMapper;
	private final FilialValidator filialValidator;

	@Transactional
	public FilialResponseDTO adicionarFilial(FilialRequestDTO filialDTO) {
		this.filialValidator.validarCamposDeFilial(filialDTO);

		Filial filialNova = this.filialMapper.toFilial(filialDTO);
		return this.filialMapper.toFilialResponseDTO(this.filialRepository.save(filialNova));
	}

	@Transactional
	public FilialResponseDTO listarFilial(String cnpj) {
		Filial filial = filialRepository.findById(cnpj)
				.orElseThrow(() -> new ResourceNotFoundException("Filial não encontrada!"));

		return this.filialMapper.toFilialResponseDTO(filial);
	}

	@Transactional
	public List<FilialResponseDTO> listarFiliais() {
		return filialRepository.findAll().stream().map(filialMapper::toFilialResponseDTO).toList();
	}

	@Transactional
	public void removerFilial(String cnpj) {
		Filial filial = this.filialRepository.findById(cnpj)
				.orElseThrow(() -> new ResourceNotFoundException("Filial não encontrada!"));

		this.filialRepository.delete(filial);
	}

	public FilialResponseDTO editarFilial(String cnpj, FilialRequestDTO filialEditado) {
		Filial filial = this.filialRepository.findById(cnpj)
				.orElseThrow(() -> new ResourceNotFoundException("Filial não encontrada!"));
		this.filialValidator.validarDadosEditado(filialEditado, cnpj);
		this.filialMapper.atualizarCampos(filial, filialEditado);

		return this.filialMapper.toFilialResponseDTO(this.filialRepository.save(filial));
	}
}
