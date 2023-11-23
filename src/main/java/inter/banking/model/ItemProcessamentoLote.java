package inter.banking.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ItemProcessamentoLote {

    /**
     * EMPROCESSAMENTO não representa um estado do serviço de pagamentos. É o estado inicial do pagamento
     * EMPROCESSAMENTO REALIZADO AGENDADO AGUARDANDO_APROVACAO APROVADO CANCELADO ERRO NAO_COMPENSADO APROVADO_NOVO_PAGAMENTO AGENDADO_REALIZADO AGENDADO_CANCELADO APROVACAO_EXPIRADA ERRO_PAGAMENTO PAGO PAGAMENTO_AGENDADO
     */
    private String status;

    /**
     * Código de barras ou linha digitável
     */
    private String codBarraLinhaDigitavel;

    /**
     * Valor a ser pago.
     */
    private BigDecimal valorPagar;

    /**
     * Data em que será pago o título.
     * Formato aceito: YYYY-MM-DD
     */
    private Date dataPagamento;

    /**
     * Data de vencimento do título.
     * Formato aceito: YYYY-MM-DD
     */
    private Date dataVencimento;

    /**
     * BOLETO DARF
     */
    private String tipoPagamento;
    private String detalhe;

    /**
     * Identificador único do pagamento ao ser registrado.
     */
    private String idTransacao;

}
