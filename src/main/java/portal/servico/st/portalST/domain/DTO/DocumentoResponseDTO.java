package portal.servico.st.portalST.domain.DTO;

import lombok.Data;

@Data
public class DocumentoResponseDTO {
	    private Long id;
	    private String tipo;
	    private String tipoArquivo; 
	    private Long tamanho;
	    private String nomeArquivo;
}
