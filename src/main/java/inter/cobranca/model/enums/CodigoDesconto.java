package inter.cobranca.model.enums;

public enum CodigoDesconto {
    /**
     * Não tem desconto.
     */
    NAOTEMDESCONTO,
    /**
     * Valor fixo até a data informada.
     */
    VALORFIXODATAINFORMADA,
    /**
     * Percentual até a data informada.
     */
    PERCENTUALDATAINFORMADA,
    /**
     * Valor por antecipação dia corrido.
     */
    VALORANTECIPACAODIACORRIDO,
    /**
     * Valor por antecipação dia útil.
     */
    VALORANTECIPACAODIAUTIL,
    /**
     * Percentual sobre o valor nominal dia corrido.
     */
    PERCENTUALVALORNOMINALDIACORRIDO,
    /**
     * Percentual sobre o valor nominal dia útil.
     */
    PERCENTUALVALORNOMINALDIAUTIL
}
