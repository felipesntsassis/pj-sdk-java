package inter.cobranca.model.enums;

public enum MotivoCancelamento {
    /**
     * cancelado por acertos
     */
    ACERTOS,
    /**
     * cancelado a pedido do cliente
     */
    APEDIDODOCLIENTE,
    /**
     * cancelado por devolução
     */
    DEVOLUCAO,
    /**
     * cancelado por ter sido pago direto ao cliente
     */
    PAGODIRETOAOCLIENTE,
    /**
     * cancelado por substituição
     */
    SUBSTITUICAO
}
