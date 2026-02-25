package portal.servico.st.portalST.domain;

import java.util.Set;

import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import portal.servico.st.portalST.domain.enums.Permissao;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "PERFIL")
public class Perfil {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String nome;
    
    @ElementCollection(targetClass = Permissao.class)
    @Enumerated(EnumType.STRING)
    @CollectionTable(name = "PERFIL_PERMISSAO",
            joinColumns = @JoinColumn(name = "perfil_id"))
    @Column(name = "permissao")
    private Set<Permissao> permissoes;
}