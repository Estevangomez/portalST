package portal.servico.st.portalST.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import portal.servico.st.portalST.domain.DTO.UsuarioResponseDTO;
import portal.servico.st.portalST.service.UsuarioService;
@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/usuarios")
@RequiredArgsConstructor
public class UsuarioController {

    private final UsuarioService service;

    
    @GetMapping("/externos")
    public ResponseEntity<List<UsuarioResponseDTO>> listarExternos() {    	
        List<UsuarioResponseDTO> lista = service.listarExternos();
        return ResponseEntity.ok(lista);
    }
    
  
}
