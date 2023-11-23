package inter.banking.model;

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
public class RespostaIncluirPix {

    private String tipoRetorno;

    /**
     * Id transação do pagamento Pix
     */
    private String endToEndId;

    /**
     * Id da tabela pagamento ou devolução Pix para aprovação
     */
    private String codigoAgendamento;

    /**
     * Data de pagamento do Pix
     */
    private String dataPagamento;

    /**
     * Data da operação do Pix
     */
    private String dataOperacao;
    private String idAprovacao;

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
