package portal.servico.st.portalST.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import portal.servico.st.portalST.domain.Documento;

public interface DocumentoRepository extends JpaRepository<Documento, Long> {
}