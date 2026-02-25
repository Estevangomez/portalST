package portal.servico.st.portalST.domain.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DocumentoRequestDTO {

	    private Long id;
	    private String tipo;
	    private String tipoArquivo; 
	    private Long tamanho;
	    private String nomeArquivo;
	
}
