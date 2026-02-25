package portal.servico.st.portalST.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import portal.servico.st.portalST.domain.Empresa;
import portal.servico.st.portalST.domain.enums.StatusEmpresa;

@Repository
public interface EmpresaRepository extends JpaRepository<Empresa, Long> {
	
	 Page<Empresa> findAllByStatus(StatusEmpresa status,Pageable pageable);
}