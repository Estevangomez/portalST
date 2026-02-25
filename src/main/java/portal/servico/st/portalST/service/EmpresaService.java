package portal.servico.st.portalST.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import portal.servico.st.portalST.domain.Documento;
import portal.servico.st.portalST.domain.Empresa;
import portal.servico.st.portalST.domain.Perfil;
import portal.servico.st.portalST.domain.Usuario;
import portal.servico.st.portalST.domain.DTO.DocumentoRequestDTO;
import portal.servico.st.portalST.domain.DTO.DocumentoResponseDTO;
import portal.servico.st.portalST.domain.DTO.EmpresaMapper;
import portal.servico.st.portalST.domain.DTO.EmpresaRequestDTO;
import portal.servico.st.portalST.domain.DTO.EmpresaResponseDTO;
import portal.servico.st.portalST.domain.enums.Permissao;
import portal.servico.st.portalST.domain.enums.StatusEmpresa;
import portal.servico.st.portalST.domain.enums.TipoPessoa;
import portal.servico.st.portalST.domain.enums.TipoUsuario;
import portal.servico.st.portalST.exceptions.RegraNegocioException;
import portal.servico.st.portalST.repository.EmpresaRepository;
import portal.servico.st.portalST.repository.PerfilRepository;

@Service
public class EmpresaService {

	private final EmpresaRepository empresaRepository;
	private final UsuarioService usuarioService;
	private final DocumetoService documentoService;
	private final PerfilRepository perfilRepository;
	private final EmpresaMapper mapper;

	public EmpresaService(EmpresaRepository empresaRepository, UsuarioService usuarioService,
			PerfilRepository perfilRepository, DocumetoService documentoService, EmpresaMapper mapper) {

		this.empresaRepository = empresaRepository;
		this.usuarioService = usuarioService;
		this.perfilRepository = perfilRepository;
		this.documentoService = documentoService;
		this.mapper = mapper;
	}

	@Transactional
	public EmpresaResponseDTO cadastrar(Long usuarioId, EmpresaRequestDTO dto) {
		Usuario usuario = usuarioService.findById(usuarioId)
				.orElseThrow(() -> new RegraNegocioException("Usuário não encontrado"));

		validarCampos(dto);
		usuarioService.validarPermissao(usuario, Permissao.EMPRESA_CADASTRO);

		Empresa empresa = mapper.toEntity(dto);

		Perfil perfil = perfilRepository.findById(dto.getPerfilId())
				.orElseThrow(() -> new RegraNegocioException("Perfil não encontrado"));

		empresa.setPerfil(perfil);
		empresa.setDataCriacao(LocalDateTime.now());
		empresa.setIntegradoSiscomex(dto.getIntegradoSiscomex());

		if (usuario.getPerfil().getNome().equals(TipoUsuario.INTERNO.name())) {

			if (!empresa.getIntegradoSiscomex()) {
				Usuario responsavel = usuarioService.findById(dto.getUsuarioResponsavelId())
						.orElseThrow(() -> new RegraNegocioException("Responsável não encontrado"));

				if (responsavel.getPerfil().getNome().equals(TipoUsuario.INTERNO.name())) {
					throw new RegraNegocioException("Responsável deve ser usuário externo");
				}
				empresa.setUsuarioResponsavel(responsavel);
				empresa.setStatus(StatusEmpresa.APROVADA);
			} else {
				empresa.setStatus(StatusEmpresa.PENDENTE);
			}

		} else {
			empresa.setUsuarioResponsavel(usuario);
			empresa.setStatus(StatusEmpresa.PENDENTE);
		}

		documentoService.validarDocumentos(dto.getDocumentos());
		Empresa salva = empresaRepository.save(empresa);

		if (dto.getDocumentos() != null && !dto.getDocumentos().isEmpty()) {
			for (DocumentoRequestDTO docDto : dto.getDocumentos()) {
				Documento novoDoc = new Documento();
				novoDoc.setNomeArquivo(docDto.getNomeArquivo());
				novoDoc.setTipo(docDto.getTipo());
				novoDoc.setTipoArquivo(docDto.getTipoArquivo());
				novoDoc.setTamanho(docDto.getTamanho());
				novoDoc.setEmpresa(empresa);
				documentoService.save(novoDoc);
			}
		}
		return mapper.toDTO(salva);
	}

	@Transactional
	public EmpresaResponseDTO editar(Long usuarioId, Long empresaId, EmpresaRequestDTO dto) {

		Usuario usuario = usuarioService.findById(usuarioId)
				.orElseThrow(() -> new RegraNegocioException("Usuário não encontrado"));

		usuarioService.validarPermissao(usuario, Permissao.EMPRESA_EDICAO);

		Empresa empresa = empresaRepository.findById(empresaId)
				.orElseThrow(() -> new RegraNegocioException("Empresa não encontrada"));

		empresa.setRazaoSocial(dto.getRazaoSocial());
		empresa.setNomeFantasia(dto.getNomeFantasia());
		empresa.setFaturamentoDireto(dto.getFaturamentoDireto());

		if (dto.getTipoPessoa() != null) {
			empresa.setTipoPessoa(TipoPessoa.valueOf(dto.getTipoPessoa()));
		}

		if (dto.getPerfilId() != null) {
			Perfil perfil = perfilRepository.findById(dto.getPerfilId())
					.orElseThrow(() -> new RegraNegocioException("Perfil não encontrado"));
			empresa.setPerfil(perfil);
		}

		return mapper.toDTO(empresa);
	}

	@Transactional
	public Page<EmpresaResponseDTO> listar(Long usuarioId, String status, Pageable pageable) {

		Usuario usuario = usuarioService.findById(usuarioId)
				.orElseThrow(() -> new RegraNegocioException("Usuário não encontrado"));

		usuarioService.validarPermissao(usuario, Permissao.EMPRESA_LISTA);

		Page<Empresa> pagina;

		if (status != null) {
			pagina = empresaRepository.findAllByStatus(StatusEmpresa.valueOf(status.toUpperCase()), pageable);
		} else {
			pagina = empresaRepository.findAll(pageable);
		}

		return pagina.map(mapper::toDTO);
	}

	@Transactional
	public void aprovarEmpresa(Long usuarioId, Long empresaId, Long responsavelId) {

		Usuario usuarioInterno = usuarioService.findById(usuarioId)
				.orElseThrow(() -> new RegraNegocioException("Usuário não encontrado"));

		if (!usuarioInterno.getPerfil().getNome().equals(TipoUsuario.INTERNO.name())) {
			throw new RegraNegocioException("Somente usuário interno pode aprovar");
		}
		Empresa empresa = empresaRepository.findById(empresaId)
				.orElseThrow(() -> new RegraNegocioException("Empresa não encontrada"));

		if (responsavelId != null) {
			Usuario responsavel = usuarioService.findById(responsavelId)
					.orElseThrow(() -> new RegraNegocioException("Responsável não encontrado"));
			empresa.setUsuarioResponsavel(responsavel);
		}

		empresa.setStatus(StatusEmpresa.APROVADA);

	}

	@Transactional
	public void reprovarEmpresa(Long usuarioId, Long empresaId) {

		Empresa empresa = empresaRepository.findById(empresaId)
				.orElseThrow(() -> new RegraNegocioException("Empresa não encontrada"));

		if (empresa.getStatus() == StatusEmpresa.REPROVADA) {
			throw new RegraNegocioException("Empresa já está reprovada");
		}

		if (empresa.getStatus() == StatusEmpresa.APROVADA) {
			throw new RegraNegocioException("Empresa aprovada não pode ser reprovada");
		}

		empresa.setStatus(StatusEmpresa.REPROVADA);
	}

	public EmpresaResponseDTO findById(Long id) {
		Empresa empresa = empresaRepository.findById(id)
				.orElseThrow(() -> new RegraNegocioException("Empresa não encontrada"));

		EmpresaResponseDTO dto = mapper.toDTO(empresa);
		if (empresa.getDocumentos() != null) {
			List<DocumentoResponseDTO> documentosDTO = empresa.getDocumentos().stream().map(doc -> {
				DocumentoResponseDTO d = new DocumentoResponseDTO();
				d.setId(doc.getId());
				d.setNomeArquivo(doc.getNomeArquivo());
				d.setTipo(doc.getTipo());
				d.setTipoArquivo(doc.getTipoArquivo());
				d.setTamanho(doc.getTamanho());
				return d;
			}).collect(Collectors.toList());

			dto.setDocumentos(documentosDTO);
		}

		return dto;
	}

	private void validarCampos(EmpresaRequestDTO dto) {
		if ("JURIDICA".equals(dto.getTipoPessoa())) {
			if (!isCnpjValido(dto.getCnpj())) {
				throw new RuntimeException("CNPJ inválido.");
			}
		}

		if ("FISICA".equals(dto.getTipoPessoa())) {			
			if (!isCpfValido(dto.getCpf())) {
			    throw new RuntimeException("CPF inválido.");
			}
		}

	}

	public static boolean isCpfValido(String cpf) {
		if (cpf == null)
			return false;		
		cpf = cpf.replaceAll("\\D", "");

		if (cpf.length() != 11 || cpf.matches("(\\d)\\1{10}"))
			return false;

		try {
			int d1 = 0, d2 = 0;
			int digit1, digit2, resto;
			int nDigito;

			for (int nCount = 1; nCount < cpf.length() - 1; nCount++) {
				nDigito = Integer.parseInt(cpf.substring(nCount - 1, nCount));
				d1 = d1 + (11 - nCount) * nDigito;
			}

			resto = (d1 % 11);
			if (resto < 2)
				digit1 = 0;
			else
				digit1 = 11 - resto;

			for (int nCount = 1; nCount < cpf.length(); nCount++) {
				nDigito = Integer.parseInt(cpf.substring(nCount - 1, nCount));
				d2 = d2 + (12 - nCount) * nDigito;
			}

			resto = (d2 % 11);
			if (resto < 2)
				digit2 = 0;
			else
				digit2 = 11 - resto;

			String nDigVerificativo = cpf.substring(cpf.length() - 2);
			String nDigResultante = String.valueOf(digit1) + String.valueOf(digit2);

			return nDigVerificativo.equals(nDigResultante);
		} catch (Exception e) {
			return false;
		}
	}

	public static boolean isCnpjValido(String cnpj) {
		if (cnpj == null)
			return false;

		cnpj = cnpj.replaceAll("\\D", "");

		if (cnpj.length() != 14 || cnpj.matches("(\\d)\\1{13}"))
			return false;

		try {
			int soma = 0;
			int peso = 5;

			for (int i = 0; i < 12; i++) {
				soma += Integer.parseInt(cnpj.substring(i, i + 1)) * peso;
				peso = (peso == 2) ? 9 : peso - 1;
			}

			int digito1 = 11 - (soma % 11);
			if (digito1 > 9)
				digito1 = 0;

			soma = 0;
			peso = 6;
			for (int i = 0; i < 13; i++) {
				soma += Integer.parseInt(cnpj.substring(i, i + 1)) * peso;
				peso = (peso == 2) ? 9 : peso - 1;
			}

			int digito2 = 11 - (soma % 11);
			if (digito2 > 9)
				digito2 = 0;

			return cnpj.substring(12).equals(String.valueOf(digito1) + String.valueOf(digito2));

		} catch (Exception e) {
			return false;
		}
	}

}
