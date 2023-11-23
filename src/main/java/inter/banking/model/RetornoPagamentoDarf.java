package inter.banking.model;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonInclude;
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
public class RetornoPagamentoDarf {

    private String codigoTransacao;
    private String tipoDarf;
    private BigDecimal valor;
    private BigDecimal valorMulta;
    private BigDecimal valorJuros;
    private BigDecimal valorTotal;
    private String tipo;
    private String periodoApuracao;
    private String dataPagamento;
    private String referencia;
    private String dataVencimento;
    private String codigoReceita;

    /**
     * REALIZADO AGENDADO AGUARDANDO_APROVACAO APROVADO AGENDADO_REALIZADO
     */
    private String statusPagamento;
    private String dataInclusao;

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
