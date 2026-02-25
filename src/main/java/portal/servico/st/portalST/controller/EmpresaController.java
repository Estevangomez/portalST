package portal.servico.st.portalST.controller;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import portal.servico.st.portalST.domain.DTO.EmpresaRequestDTO;
import portal.servico.st.portalST.domain.DTO.EmpresaResponseDTO;
import portal.servico.st.portalST.service.EmpresaService;
@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/empresas")
@RequiredArgsConstructor
public class EmpresaController {

    private final EmpresaService service;  
    
    @GetMapping
    public ResponseEntity<Page<EmpresaResponseDTO>> listar(
            @RequestHeader( "usuario-id") Long usuarioId,
            @RequestParam(required = false) String status,            
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {

        Pageable pageable = PageRequest.of(page, size);

        Page<EmpresaResponseDTO> resultado =
                service.listar(usuarioId, status, pageable);

        return ResponseEntity.ok(resultado);
    }
    
    @PostMapping
    public ResponseEntity<EmpresaResponseDTO> cadastrar(
            @RequestHeader("usuario-id") Long usuarioId,
            @Valid @RequestBody EmpresaRequestDTO dto) {
        EmpresaResponseDTO response = service.cadastrar(usuarioId, dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
    
    @PatchMapping("/aprovar/{id}")
    public ResponseEntity<String> aprovar(
           @RequestHeader("usuario-id") Long usuarioId,
           @RequestParam("responsavel-id") Long ResponsavelId,
           @PathVariable(name = "id") Long id) {
        service.aprovarEmpresa(usuarioId, id,ResponsavelId);
        return ResponseEntity.status(HttpStatus.OK).body("");
    }
    
    @PatchMapping("/reprovar/{id}")
    public ResponseEntity<String> reprovar(
           @RequestHeader("usuario-id") Long usuarioId,           
           @PathVariable(name = "id") Long id) {
        service.reprovarEmpresa(usuarioId, id);
        return ResponseEntity.status(HttpStatus.OK).body("");
    }
    
    @GetMapping("/editar/{id}")
    public ResponseEntity<EmpresaResponseDTO> findById(
            @PathVariable Long id) {    	
    	return ResponseEntity.ok(service.findById(id));
    }
    
    @PatchMapping("/{id}")
    public ResponseEntity<EmpresaResponseDTO> editar(
            @RequestHeader("usuario-id") Long usuarioId,
            @PathVariable Long id,
            @RequestBody EmpresaRequestDTO dto) {
        EmpresaResponseDTO response =
                service.editar(usuarioId, id, dto);
        return ResponseEntity.ok(response);
    }
}
