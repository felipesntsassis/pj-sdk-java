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
public class RespostaIncluirPagamentosLote {

    /**
     * Id do pagamento de um lote específico.
     */
    private String idLote;

    /**
     * EMPROCESSAMENTO PROCESSADOCOMERRO PROCESSADOSEMERRO
     */
    private String status;

    /**
     * Identificador do lote
     */
    private String meuIdentificador;

    /**
     * Qtde de pagamentos existentes no lote.
     */
    private Integer qtdePagamentos;

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
