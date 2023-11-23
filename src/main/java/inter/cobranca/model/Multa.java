package inter.cobranca.model;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonInclude;
import inter.cobranca.model.enums.CodigoMulta;
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
public class Multa {

    /**
     * Código de Multa do título.
     */
    private CodigoMulta codigoMulta;

    /**
     * Data da Multa do título.
     * <p>Obrigatório se informado código de multa VALORFIXO ou PERCENTUAL.</p>
     * <p>Deve ser maior que o vencimento e marca a data de início de cobrança
     * de multa (incluindo essa data)</p>
     * <p>Formato aceito: YYYY-MM-DD</p>
     */
    private String data;

    /**
     * Taxa Percentual de Multa do título. Obrigatória se informado código
     * de multa PERCENTUAL
     * <p>Deve ser 0 para código de multa NAOTEMMULTA</p>
     */
    private BigDecimal taxa;

    /**
     * Valor de Multa expresso na moeda do título.
     * <p>Obrigatório se informado código de multa VALORFIXO</p>
     * <p>Deve ser 0 para código de multa NAOTEMMULTA</p>
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
