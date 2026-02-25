package portal.servico.st.portalST.domain.DTO;

import org.springframework.stereotype.Component;

import portal.servico.st.portalST.domain.Empresa;
import portal.servico.st.portalST.domain.enums.TipoPessoa;

@Component
public class EmpresaMapper {

	public Empresa toEntity(EmpresaRequestDTO dto) {
	    Empresa empresa = new Empresa();

	    empresa.setTipoPessoa(TipoPessoa.valueOf(dto.getTipoPessoa()));
	    empresa.setRazaoSocial(dto.getRazaoSocial());
	    empresa.setNomeFantasia(dto.getNomeFantasia());
	    empresa.setCnpj(dto.getCnpj());
	    empresa.setCpf(dto.getCpf());	    
	    empresa.setFaturamentoDireto(dto.getFaturamentoDireto());

	    return empresa;
	}
	
    public EmpresaResponseDTO toDTO(Empresa empresa) {
        EmpresaResponseDTO dto = new EmpresaResponseDTO();
        dto.setId(empresa.getId());
        dto.setPerfilId(empresa.getPerfil().getId());
        dto.setPerfilNome(empresa.getPerfil().getNome());
        dto.setTipoPessoa(empresa.getTipoPessoa().name());
        dto.setRazaoSocial(empresa.getRazaoSocial());
        dto.setNomeFantasia(empresa.getNomeFantasia());        
        dto.setStatus(empresa.getStatus().name());
        dto.setCpf(empresa.getCpf());
        dto.setCnpj(empresa.getCnpj());
        dto.setFaturamentoDireto(empresa.getFaturamentoDireto());
        dto.setIntegradoSiscomex(empresa.getIntegradoSiscomex());
        dto.setDataCriacao(empresa.getDataCriacao());
       

        return dto;
    }
}