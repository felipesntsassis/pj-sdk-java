package inter.banking.model;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.HashMap;
import java.util.Map;

@SuperBuilder
@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PagamentoDarf {

    /**
     * Campo para informar o cpf ou cnpj do pagador
     */
    private String cnpjCpf;

    /**
     * Campo para informar o código da receita
     */
    private String codigoReceita;

    /**
     * Campo para informar a data de vencimento da DARF
     * <p>Formato aceito: YYYY-MM-DD</p>
     */
    private String dataVencimento;

    /**
     * Campo para informar a descrição
     */
    private String descricao;

    /**
     * Campo para informar o nome da empresa
     */
    private String nomeEmpresa;

    /**
     * Campo para informar o telefone da empresa
     */
    private String telefoneEmpresa;

    /**
     * Campo para informar o período de apuração da DARF
     * <p>Formato aceito: YYYY-MM-DD</p>
     */
    private String periodoApuracao;

    /**
     * Campo para informar o valor principal
     */
    private String valorPrincipal;

    /**
     * Campo para informar o valor da multa
     */
    private String valorMulta;

    /**
     * Campo para informar o valor do juros
     */
    private String valorJuros;

    /**
     * Campo para informar a referência da DARF
     */
    private String referencia;

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
