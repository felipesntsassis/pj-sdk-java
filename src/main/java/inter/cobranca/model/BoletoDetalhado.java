package inter.cobranca.model;

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
public class BoletoDetalhado extends Boleto {

    /**
     * Nome do beneficiário.
     */
    private String nomeBeneficiario;

    /**
     * CNPJ do beneficiário.
     */
    private String cnpjCpfBeneficiario;

    /**
     * tipoPessoaBeneficiario
     */
    private String tipoPessoaBeneficiario;

    /**
     * Conta corrente do boleto emitido
     */
    private String contaCorrente;

    /**
     * Identificador único do boleto emitido.
     */
    private String nossoNumero;

    /**
     * Situacao do boleto.
     */
    private String situacao;

    /**
     * Data referente à situação do boleto.
     * Formato aceito: YYYY-MM-DD
     */
    private String dataHoraSituacao;

    /**
     * Data referente à emissão do boleto.
     * Formato aceito: YYYY-MM-DD
     */
    private String dataEmissao;

    /**
     * Data referente ao limite de pagamento do boleto.
     * Formato aceito: YYYY-MM-DD
     */
    private String dataLimite;

    /**
     * Codigo de Espécie do boleto.
     */
    private String codigoEspecie;

    /**
     * Codigo de barras do boleto.
     */
    private String codigoBarras;

    /**
     * Linha digitável do boleto.
     */
    private String linhaDigitavel;

    /**
     * Origem da requisição que gerou o boleto.
     */
    private String origem;

    /**
     * Motivo do cancelamento.
     */
    private String motivoCancelamento;

}
