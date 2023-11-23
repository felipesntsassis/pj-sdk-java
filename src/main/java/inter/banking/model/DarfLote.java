package inter.banking.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@SuperBuilder
@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class DarfLote extends PagamentoDarf implements ItemLote {

    private final String tipoPagamento = "DARF";

    private String detalhe;

    /**
     * Identificador único do pagamento ao ser registrado.
     */
    private String idTransacao;

    /**
     * EMPROCESSAMENTO não representa um estado do serviço de pagamentos. É o estado inicial do pagamento
     * EMPROCESSAMENTO REALIZADO AGENDADO AGUARDANDO_APROVACAO APROVADO CANCELADO ERRO NAO_COMPENSADO APROVADO_NOVO_PAGAMENTO AGENDADO_REALIZADO AGENDADO_CANCELADO APROVACAO_EXPIRADA ERRO_PAGAMENTO PAGO PAGAMENTO_AGENDADO
     */
    private String status;

}
