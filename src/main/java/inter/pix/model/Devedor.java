package inter.pix.model;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashMap;
import java.util.Map;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
/**
 * Os campos aninhados sob o objeto devedor são opcionais e identificam a pessoa ou a instituição a quem a cobrança está endereçada.
 * Não identifica, necessariamente, quem irá efetivamente realizar o pagamento.
 * Não é permitido que o campo devedor.cpf e campo devedor.cnpj estejam preenchidos ao mesmo tempo.
 * Se o campo devedor.nome está preenchido, então deve existir ou um devedor.cpf ou um campo devedor.cnpj preenchido.
 */
public class Devedor {

    /**
     * REQUERIDO se for PF
     * CPF do usuário.
     */
    private String cpf;

    /**
     * REQUERIDO se for PJ
     * CNPJ do usuário.
     */
    private String cnpj;

    /**
     * REQUERIDO
     * Nome do usuário.
     */
    private String nome;

    private String email;
    private String cidade;
    private String uf;
    private String cep;
    private String logradouro;

    private Map<String, Object> camposAdicionais = new HashMap<>();

    @JsonAnyGetter
    public Map<String, Object> getCamposAdicionais() {
        return camposAdicionais;
    }

    @JsonAnySetter
    public void setCamposAdicionais(final String nome, final Object valor) {
        this.camposAdicionais.put(nome, valor);
    }

}
