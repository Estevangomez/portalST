package portal.servico.st.portalST.domain;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import portal.servico.st.portalST.domain.enums.StatusEmpresa;
import portal.servico.st.portalST.domain.enums.TipoPessoa;

@SuppressWarnings("serial")
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "EMPRESA")
public class Empresa implements Serializable {

	    @Id
	    @GeneratedValue(strategy = GenerationType.IDENTITY)
	    private Long id;
	    @Enumerated(EnumType.STRING)
	    private TipoPessoa tipoPessoa;
	    @NotBlank(message = "A razão social não pode estar em branco")
	    private String razaoSocial;
	    @NotBlank(message = "O nome fantasia não pode estar em branco")
	    private String nomeFantasia;
	    @Size(max = 14)
	    @Column(length = 14)
	    private String cnpj;
	    @Size(max = 11)
	    @Column(length = 11)
	    private String cpf;	  
	    @ManyToOne
	    @JoinColumn(name = "perfil_id", nullable = false)
	    private Perfil perfil;	    
	    @ManyToOne
	    @JoinColumn(name = "usuario_responsavel_id")
	    private Usuario usuarioResponsavel;
	    @OneToMany(mappedBy = "empresa", cascade = CascadeType.ALL)
	    private List<Documento> documentos = new ArrayList<>();	    
	    @Enumerated(EnumType.STRING)
	    private StatusEmpresa status;
	    private Boolean faturamentoDireto;
	    private LocalDateTime dataCriacao;	    
	    private Boolean integradoSiscomex;
	

}
