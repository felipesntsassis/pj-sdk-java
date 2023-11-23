package inter.banking.model;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

@SuperBuilder
@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PagamentoBoleto {

    /**
     * REQUERIDO
     * Código de barras ou linha digitável.
     */
    private String codBarraLinhaDigitavel;

    /**
     * REQUERIDO
     * Valor a ser pago.
     */
    private BigDecimal valorPagar;

    /**
     * Data para efetivar o pagamento. Se não informada, o pagamento será feito no mesmo dia.
     * Formato aceito: YYYY-MM-DD
     */
    private String dataPagamento;

    /**
     * REQUERIDO
     * Data de vencimento do título.
     * Formato aceito: YYYY-MM-DD
     */
    private String dataVencimento;

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
