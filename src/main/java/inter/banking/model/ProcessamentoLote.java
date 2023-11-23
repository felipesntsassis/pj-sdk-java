package inter.banking.model;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ProcessamentoLote {

    /**
     * Conta corrente responsável pelo pagamento do lote.
     */
    private String contaCorrente;

    /**
     * Data em que o lote foi cadastrado.
     */
    private String dataCriacao;
    private List<ItemLote> pagamentos;

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
