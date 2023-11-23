package inter.cobranca.model;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonInclude;
import inter.cobranca.model.enums.CodigoMora;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Mora {

    /**
     * Código de Mora do título.
     */
    private CodigoMora codigoMora;

    /**
     * Data da Mora do título.
     * <p>Obrigatório se informado código de mora VALORDIA, TAXAMENSAL ou CONTROLEDOBANCO.</p>
     * <p>Deve ser maior que o vencimento e marca a data de início de cobrança
     * de mora (incluindo essa data)</p>
     * <p>Formato aceito: YYYY-MM-DD</p>
     */
    private String data;

    /**
     * Percentual de Mora do título.
     * <p>Obrigatória se informado código de mora TAXAMENSAL</p>
     */
    private BigDecimal taxa;

    /**
     * Valor de Mora expresso na moeda do título.
     * <p>Obrigatório se informado código de mora TAXAMENSAL</p>
     * <p>Deve ser 0 para código de mora ISENTO</p>
     */
    private BigDecimal valor;

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
