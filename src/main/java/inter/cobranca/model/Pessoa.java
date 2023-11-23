package inter.cobranca.model;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonInclude;
import inter.cobranca.model.enums.TipoPessoa;
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
public class Pessoa {

    /**
     * REQUERIDO
     * <p>CPF/CNPJ do pagador do título</p>
     * <p>CNPJ: NNNNNNNNNFFFFCC</p>
     * <p>CPF : 0000NNNNNNNNNCC,</p>
     * <p>NNNNNNNNN é a raiz do CNPJ/CPF</p>
     * <p>FFFF = filial do CNPJ</p>
     * <p>CC = dígitos de controle</p>
     */
    private String cpfCnpj;

    /**
     * REQUERIDO
     * <p>Tipo do pagador</p>
     */
    private TipoPessoa tipoPessoa;

    /**
     * REQUERIDO
     */
    private String nome;

    /**
     * REQUERIDO
     */
    private String endereco;
    private String numero;
    private String complemento;
    private String bairro;

    /**
     * REQUERIDO
     */
    private String cidade;

    /**
     * REQUERIDO
     */
    private String uf;

    /**
     * REQUERIDO
     */
    private String cep;
    private String email;
    private String ddd;
    private String telefone;

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
