package portal.servico.st.portalST.domain.DTO;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class EmpresaRequestDTO {
	    private String tipoPessoa;
	    private String razaoSocial;
	    private String nomeFantasia;
	    private String cnpj;
	    private String cpf;	   
	    private String status;	   
	    private Long perfilId;
	    private Long usuarioResponsavelId;
	    private Boolean faturamentoDireto;
	    private Boolean integradoSiscomex;
	    private List<DocumentoRequestDTO> documentos;
	    
	    
	    
	    
	    
	    

}
