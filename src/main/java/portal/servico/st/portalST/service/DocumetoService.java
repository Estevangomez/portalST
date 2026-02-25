package portal.servico.st.portalST.service;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import portal.servico.st.portalST.domain.Documento;
import portal.servico.st.portalST.domain.DTO.DocumentoRequestDTO;
import portal.servico.st.portalST.exceptions.RegraNegocioException;
import portal.servico.st.portalST.repository.DocumentoRepository;

@Service
public class DocumetoService { 
	
	 private final DocumentoRepository documentoRepository;
	 
	 public DocumetoService(DocumentoRepository documentoRepository) {
		 this.documentoRepository = documentoRepository;
	 }
	 
       public void validarDocumentos(List<DocumentoRequestDTO> documentos) {

	    if (documentos == null || documentos.isEmpty()) {
	        throw new RegraNegocioException("É necessário enviar os arquivos obrigatórios");
	    }

	    List<String> tiposValidos = List.of("application/pdf", "image/png", "image/jpg", "image/jpeg");

	    Set<String> nomesArquivos = new HashSet<>();	   

	    for (DocumentoRequestDTO doc : documentos) {       
	        if (!tiposValidos.contains(doc.getTipoArquivo().toLowerCase())) {
	            throw new RegraNegocioException("São válidos somente arquivos do tipo: pdf, png, jpg ou jpeg.");
	        }
	
	        if (!nomesArquivos.add(doc.getNomeArquivo())) {
	            throw new RegraNegocioException("Arquivos duplicados");
	        }
	    }

	  
	}	
	
       
    @Transactional
   	public Optional<Documento> findById(Long idDoc) {
   		return documentoRepository.findById(idDoc);
	}
       
	@Transactional
	public void save(Documento doc) {
		documentoRepository.save(doc);
	}
	
	@Transactional
	public void delete(Long idDoc) {
		documentoRepository.deleteById(idDoc);
	}
  
}
