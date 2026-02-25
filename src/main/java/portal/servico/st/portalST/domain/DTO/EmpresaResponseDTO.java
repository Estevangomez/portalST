package portal.servico.st.portalST.domain.DTO;

import java.time.LocalDateTime;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EmpresaResponseDTO {
    private Long id;

    private String tipoPessoa;

    private String razaoSocial;
    private String nomeFantasia;

    private String cnpj;
    private String cpf;

    private Long perfilId;
    private String perfilNome;

    private Long usuarioResponsavelId;
    private String usuarioResponsavelNome;

    private String status;

    private Boolean faturamentoDireto;

    private LocalDateTime dataCriacao;

    private Boolean integradoSiscomex;

    private List<DocumentoResponseDTO> documentos;
    
    
    
    
}
