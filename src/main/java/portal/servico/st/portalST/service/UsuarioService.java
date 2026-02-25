package portal.servico.st.portalST.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import portal.servico.st.portalST.domain.Usuario;
import portal.servico.st.portalST.domain.DTO.UsuarioResponseDTO;
import portal.servico.st.portalST.domain.enums.Permissao;
import portal.servico.st.portalST.exceptions.RegraNegocioException;
import portal.servico.st.portalST.repository.UsuarioRepository;

@Service
public class UsuarioService { 
	
	private UsuarioRepository usuarioRepository;
	
	  public UsuarioService(UsuarioRepository usuarioRepository) {
	        this.usuarioRepository = usuarioRepository;	
	    }

	public Optional<Usuario> findById(Long id) {
		return usuarioRepository.findById(id);
	}
	
	public List<UsuarioResponseDTO> listarExternos() {
        return usuarioRepository.findByPerfilNome("EXTERNO").stream()
                .map(u -> new UsuarioResponseDTO(u.getId(), u.getNome(), u.getCertificado(), u.getPerfil().getNome(), u.getPerfil().getId()))
                .collect(Collectors.toList());
    }

	
	public void validarPermissao(Usuario usuario, Permissao permissao) {
	    if (usuario.getPerfil() == null ||
	        usuario.getPerfil().getPermissoes() == null ||
	        !usuario.getPerfil().getPermissoes().contains(permissao)) {

	        throw new RegraNegocioException(
	            "Usuário não possui permissão: " + permissao
	        );
	    }
	}	

  
}
