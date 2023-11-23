package inter.cobranca.model.enums;

public enum TipoDataBoleto {
    /**
     * Filtro de data pelo vencimento. (Default)
     */
    VENCIMENTO,
    /**
     * Filtro de data pela emissão.
     */
    EMISSAO,
    /**
     * Filtro de data pela mudança de situação.
     */
    SITUACAO
}
