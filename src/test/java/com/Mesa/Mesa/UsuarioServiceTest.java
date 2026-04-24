package com.Mesa.Mesa;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.project.Mesa.Controller.dto.usuario.UsuarioRequestDTO;
import com.project.Mesa.Controller.dto.usuario.UsuarioResponseDTO;
import com.project.Mesa.Mapper.impl.UsuarioMapperImpl;
import com.project.Mesa.Model.Filial;
import com.project.Mesa.Model.Usuario;
import com.project.Mesa.Repository.FilialRepository;
import com.project.Mesa.Repository.UsuarioRepository;
import com.project.Mesa.Service.UsuarioService;
import com.project.Mesa.Service.exception.ResourceNotFoundException;
import com.project.Mesa.Validation.validators.CNPJValidator;
import com.project.Mesa.Validation.validators.usuario.UsuarioValidator;

@ExtendWith(MockitoExtension.class)
public class UsuarioServiceTest {
	@InjectMocks
    private UsuarioService usuarioService;

    @Mock
    private UsuarioRepository usuarioRepository;

    @Mock
    private UsuarioMapperImpl usuarioMapper;

    @Mock
    private UsuarioValidator usuarioValidator;
    
    @Mock
    private PasswordEncoder passwordEncoder;
    
    @Mock 
    private FilialRepository filialRepository;
    
    @Mock
    private CNPJValidator cnpjValidator;
    
    
    UsuarioRequestDTO request;
    Usuario usuarioSalvo;
    UsuarioResponseDTO responseEsperado;
    Long id = 1L;
    
    @BeforeEach
    void setup() {
    	 request = new UsuarioRequestDTO("Matheus2", "matheus@email.com", "X#x123456", "Marketing", "98.765.432/0001-15");
         usuarioSalvo = new Usuario();
         usuarioSalvo.setId(1L);
         usuarioSalvo.setLogin("matheus@email.com");
         usuarioSalvo.setPassword("X#x123456");
         usuarioSalvo.setCargo("Marketing");
         responseEsperado = new UsuarioResponseDTO(1L, "Matheus", "matheus@email.com", "Marketing", "98.765.432/0001-15");
    }
    
    @Test
    @DisplayName("Deve lançar exceção quando o request for nulo")
    void deveLancarExcecaoQuandoRequestForNulo() {

        doThrow(new RuntimeException("Usuário nulo"))
                .when(usuarioValidator).validaCamposdoUsuario(null);

        assertThrows(RuntimeException.class,
                () -> usuarioService.adicionarUsuario(null));

        verify(usuarioValidator).validaCamposdoUsuario(null);
        verifyNoInteractions(usuarioMapper);
        verifyNoInteractions(usuarioRepository);
    }
    
    @Test
    @DisplayName("Deve lançar exceção quando a validação de campos falhar")
    void deveLancarExcecaoQuandoValidacaoFalhar() {

        doThrow(new RuntimeException("Campos inválidos"))
                .when(usuarioValidator).validaCamposdoUsuario(request);

        assertThrows(RuntimeException.class,
                () -> usuarioService.adicionarUsuario(request));

        verify(usuarioValidator).validaCamposdoUsuario(request);
        verifyNoInteractions(usuarioMapper);
        verifyNoInteractions(usuarioRepository);
    }
    
    @Test
    @DisplayName("Deve Criar Usuario com Sucesso se Existir")
    void deveCriarUsuarioComSucesso_quandoFilialExiste() {
        doNothing().when(usuarioValidator).validaCamposdoUsuario(request);

        when(usuarioMapper.toUsuario(request)).thenReturn(usuarioSalvo);
        when(usuarioRepository.save(usuarioSalvo)).thenReturn(usuarioSalvo);
        when(usuarioMapper.toUsuarioResponseDTO(usuarioSalvo)).thenReturn(responseEsperado);
        when(cnpjValidator.validarCNPJ(anyString())).thenReturn("98.765.432/0001-15");
        when(filialRepository.findById(any()))
        .thenReturn(Optional.of(new Filial()));
        when(passwordEncoder.encode(anyString()))
        .thenReturn("senhacriptografada");
        
        UsuarioResponseDTO response = usuarioService.adicionarUsuario(request);
       
        assertNotNull(response);
        assertEquals("Matheus", response.nome());
        assertEquals("matheus@email.com", response.login());

        verify(usuarioValidator, atLeastOnce()).validaCamposdoUsuario(request);
        verify(usuarioMapper, atLeastOnce()).toUsuario(request);
        verify(usuarioRepository, atLeastOnce()).save(usuarioSalvo);
        verify(usuarioMapper, atLeastOnce()).toUsuarioResponseDTO(usuarioSalvo);
        verify(passwordEncoder).encode(request.password());
    }
    
    @Test
    @DisplayName("Deve Lançarr exceção se filial não existir")
    void deveLancarException_quandoFilialNaoExiste() {

        doThrow(new ResourceNotFoundException("Filial não encontrada"))
            .when(usuarioValidator).validaCamposdoUsuario(request);

        assertThrows(ResourceNotFoundException.class, () -> {
            usuarioService.adicionarUsuario(request);
        });

        verify(usuarioRepository, never()).save(any());
    }
    
    @Test
    @DisplayName("Deve atualizar Usuarios")
    void deveAtualizarUsuario_quandoDadosValidos() {

        when(usuarioRepository.findById(id))
            .thenReturn(Optional.of(usuarioSalvo));

        doNothing().when(usuarioValidator)
            .validaDadosEditados(request, id);

        doNothing().when(usuarioMapper)
            .atualizaCampos(usuarioSalvo, request);

        when(usuarioRepository.save(usuarioSalvo))
            .thenReturn(usuarioSalvo);

        when(usuarioMapper.toUsuarioResponseDTO(usuarioSalvo))
            .thenReturn(responseEsperado);

        UsuarioResponseDTO response =
            usuarioService.atualizarUsuario(id, request);

        assertNotNull(response);
        verify(usuarioRepository).save(usuarioSalvo);
    }
    
    @Test
    @DisplayName("Deve lançar exceção quando usuario não for encontrado.")
    void deveLancarException_quandoAtualizarUsuarioInexistente() {

        when(usuarioRepository.findById(id))
            .thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> {
            usuarioService.atualizarUsuario(id, request);
        });
    }
    
}
