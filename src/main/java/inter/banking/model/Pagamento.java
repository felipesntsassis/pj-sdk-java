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
public class Pagamento {

    private String codigoTransacao;
    private String codigoBarra;
    private String tipo;
    private String dataVencimentoDigitada;
    private String dataVencimentoTitulo;
    private String dataInclusao;
    private String dataPagamento;
    private BigDecimal valorPago;
    private BigDecimal valorNominal;

    /**
     * REALIZADO AGENDADO AGUARDANDO_APROVACAO APROVADO AGENDADO_REALIZADO
     */
    private String statusPagamento;
    private Integer aprovacoesNecessarias;
    private Integer aprovacoesRealizadas;
    private String cpfCnpjBeneficiario;
    private String nomeBeneficiario;
    private String contaCorrente;

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
