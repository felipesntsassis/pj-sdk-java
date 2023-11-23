package inter.cobranca.model;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonInclude;
import inter.cobranca.model.enums.CodigoDesconto;
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
public class Desconto {

    /**
     * Código de Desconto do título.
     */
    private CodigoDesconto codigoDesconto;

    /**
     * Data de Desconto do título.
     * <p>Obrigatório para códigos de desconto VALORFIXODATAINFORMADA e PERCENTUALDATAINFORMADA. Não informar para os demais</p>
     * <p>Formato aceito: YYYY-MM-DD</p>
     */
    private String data;

    /**
     * Taxa Percentual de Desconto do título.
     * <p>Obrigatório para códigos de desconto PERCENTUALDATAINFORMADA,
     * PERCENTUALVALORNOMINALDIACORRIDO e PERCENTUALVALORNOMINALDIAUTIL</p>
     */
    private BigDecimal taxa;

    /**
     * Valor de Desconto, expresso na moeda do título.
     * <p>Obrigatório para códigos de desconto VALORFIXODATAINFORMADA,
     * VALORANTECIPACAODIACORRIDO e VALORANTECIPACAODIAUTIL</p>
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
