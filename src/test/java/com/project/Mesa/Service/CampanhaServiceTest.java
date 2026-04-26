package com.project.Mesa.Service;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import com.project.Mesa.Controller.dto.campanha.CampanhaResponseDTO;
import com.project.Mesa.Mapper.impl.CampanhaMapperImpl;
import com.project.Mesa.Model.Campanha;
import com.project.Mesa.Model.Filial;
import com.project.Mesa.Model.Usuario;
import com.project.Mesa.Repository.CampanhaRepository;
import com.project.Mesa.Repository.UsuarioRepository;
import com.project.Mesa.Service.exception.ResourceNotFoundException;

@ExtendWith(MockitoExtension.class)
public class CampanhaServiceTest {
	@InjectMocks
	private CampanhaService campanhaService;
	@Mock
	private CampanhaRepository campanhaRepository;
	@Mock
	private CampanhaMapperImpl campanhaMapper;
	@Mock
	private UsuarioRepository usuarioRepository;
	
	private final String PAGINA = "home";

    @AfterEach
    void limparContexto() {
        SecurityContextHolder.clearContext();
    }

    @Test
    @DisplayName("Deve retornar campanhas da página quando usuário for manager")
    void deveRetornarCampanhas_quandoUsuarioForManager() {

     
        Authentication auth = mock(Authentication.class);
        SecurityContext context = mock(SecurityContext.class);

        when(context.getAuthentication()).thenReturn(auth);
        SecurityContextHolder.setContext(context);
        
        Collection<GrantedAuthority> authorities =
        	    List.of(new SimpleGrantedAuthority("ROLE_MANAGER"));
        
        doReturn(authorities).when(auth).getAuthorities();
        
        when(auth.getName()).thenReturn("admin");
        
        
        Usuario usuario = new Usuario();
        usuario.setLogin("admin");

        when(usuarioRepository.findByUsername("admin"))
                .thenReturn(Optional.of(usuario));

        Campanha campanha = new Campanha();
        CampanhaResponseDTO dto = mock(CampanhaResponseDTO.class);

        when(campanhaRepository.findByPagina(PAGINA))
                .thenReturn(List.of(campanha));

        when(campanhaMapper.campanhaResponseDTO(campanha))
                .thenReturn(dto);

       
        List<CampanhaResponseDTO> response =
                campanhaService.ListarCampanhaPorPagina(PAGINA);

        
        assertNotNull(response);
        verify(campanhaRepository).findByPagina(PAGINA);
    }
    
    @Test
    @DisplayName("Deve aplicar regra especial para CarlaFerreira")
    void deveAplicarRegraEspecial_paraCarlaFerreira() {

        Authentication auth = mock(Authentication.class);
        SecurityContext context = mock(SecurityContext.class);

        when(context.getAuthentication()).thenReturn(auth);
        SecurityContextHolder.setContext(context);

        when(auth.getName()).thenReturn("CarlaFerreira");
        Collection<GrantedAuthority> authorities =
        	    List.of(new SimpleGrantedAuthority("ROLE_USER"));
        
        doReturn(authorities).when(auth).getAuthorities();
        

        Usuario usuario = new Usuario();
        usuario.setLogin("CarlaFerreira");

        Filial filial = new Filial();
        filial.setCnpj("123");

        usuario.setEmpresa(filial);

        when(usuarioRepository.findByUsername("CarlaFerreira"))
                .thenReturn(Optional.of(usuario));

        Campanha campanha = new Campanha();
        CampanhaResponseDTO dto = mock(CampanhaResponseDTO.class);

        when(campanhaRepository.findByPaginaAndEmpresaCnpjIn(any(), any()))
                .thenReturn(List.of(campanha));

        when(campanhaMapper.campanhaResponseDTO(campanha))
                .thenReturn(dto);

        campanhaService.ListarCampanhaPorPagina(PAGINA);

        verify(campanhaRepository)
                .findByPaginaAndEmpresaCnpjIn(anyString(), any());
    }

    @Test
    @DisplayName("Deve retornar campanhas filtradas por CNPJ para usuário comum")
    void deveRetornarCampanhasFiltradas_paraUsuarioComum() {

        Authentication auth = mock(Authentication.class);
        SecurityContext context = mock(SecurityContext.class);

        when(context.getAuthentication()).thenReturn(auth);
        SecurityContextHolder.setContext(context);

        when(auth.getName()).thenReturn("usuario");
        Collection<GrantedAuthority> authorities =
        	    List.of(new SimpleGrantedAuthority("ROLE_USER"));
        
        doReturn(authorities).when(auth).getAuthorities();

        Usuario usuario = new Usuario();
        usuario.setLogin("usuario");

        Filial filial = new Filial();
        filial.setCnpj("999");

        usuario.setEmpresa(filial);

        when(usuarioRepository.findByUsername("usuario"))
                .thenReturn(Optional.of(usuario));

        Campanha campanha = new Campanha();
        CampanhaResponseDTO dto = mock(CampanhaResponseDTO.class);

        when(campanhaRepository.findByPaginaAndEmpresaCnpj(any(), any()))
                .thenReturn(List.of(campanha));

        when(campanhaMapper.campanhaResponseDTO(campanha))
                .thenReturn(dto);

        campanhaService.ListarCampanhaPorPagina(PAGINA);

        verify(campanhaRepository)
                .findByPaginaAndEmpresaCnpj(PAGINA, "999");
    }
    
    @Test
    @DisplayName("Deve lançar exceção quando usuário não for encontrado")
    void deveLancarException_quandoUsuarioNaoEncontrado() {

        Authentication auth = mock(Authentication.class);
        SecurityContext context = mock(SecurityContext.class);

        when(context.getAuthentication()).thenReturn(auth);
        SecurityContextHolder.setContext(context);

        when(auth.getName()).thenReturn("inexistente");

        when(usuarioRepository.findByUsername("inexistente"))
                .thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> {
            campanhaService.ListarCampanhaPorPagina(PAGINA);
        });
    }

    
    @Test
    @DisplayName("Deve remover campanha com sucesso")
    void deveRemoverCampanha() {

        Campanha campanha = new Campanha();

        when(campanhaRepository.findById(1L))
            .thenReturn(Optional.of(campanha));

        campanhaService.removerCampanha(1L);

        verify(campanhaRepository).delete(campanha);
    }
    
    @Test
    @DisplayName("Deve lançar exceção ao remover campanha inexistente")
    void deveLancarException_quandoRemoverCampanhaInexistente() {

        when(campanhaRepository.findById(1L))
            .thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> {
            campanhaService.removerCampanha(1L);
        });

        verify(campanhaRepository, never()).delete(any());
    }
	
}
