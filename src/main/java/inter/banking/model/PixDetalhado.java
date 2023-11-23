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
public class PixDetalhado {

    private String nome;
    private String cpfCnpj;

    /**
     * Conta do recebedor
     */
    private String nroContaRecebedor;

    /**
     * Código da agência do recebedor
     */
    private String codAgenciaRecebedor;
    private String nomeInstituicao;
    private String ispb;
    private String dataMovimento;
    private String data;
    private String hora;
    private String identificador;

    /**
     * Descrição complementar ao movimento
     */
    private String descricao;
    private String numeroMovimento;

    /**
     * Valor do pagamento Pix
     */
    private BigDecimal valor;

    /**
     * Valor disponível de devolucao. Em caso de pix troco esse campo representa o valor da compra para devolução
     */
    private BigDecimal valorDisponivelDevolucao;

    /**
     * Indicador de movimento elegível para devolução, se verdadeiro pode ser realizada uma devolução parcial ou total do movimento
     */
    private Boolean isElegivelDevolucao;

    /**
     * Indicador de movimento de devolução
     */
    private Boolean isDevolucao;

    /**
     * Natureza do movimento: [C|D]
     */
    private String natureza;

    /**
     * Origem do movimento: [MANUAL|QR_CODE|CHAVE|DEVOLUCAO]
     * <p>MANUAL QR_CODE DEVOLUCAO CHAVE INIC_PAG INIC_PAG_QR_CODE INIC_PAG_MANU INIC_PAG_CHAVE</p>
     */
    private String origemMovimento;

    /**
     * Número único de operação.
     */
    private String endToEnd;

    /**
     * End To End Original - Para operações de devolução.
     */
    private String endToEndOriginal;

    /**
     * Referência Interna Obtida do Processamento de QR Code
     */
    private String referenciaInterna;

    /**
     * Chave de endereçamento
     */
    private String chave;

    /**
     * Tipo da chava utilizada no pagamento
     */
    private String tipoChave;

    /**
     * Campo livre para informações do pagador ao recebedor
     */
    private String campoLivre;

    /**
     * Instituição que iniciou o pagamento via OpenBanking
     */
    private String instituicaoIniciadora;

    /**
     * Forma de pagamento quando OpenBanking (default PIX)
     */
    private String formaPagamento;

    /**
     * Forma pagamento quando Openbanking (default OpenBanking)
     */
    private String pagoVia;

    /**
     * IPAY GSCB OTHR
     */
    private String finalidade;

    /**
     * Valor em espécie
     */
    private BigDecimal valorEmEspecie;

    /**
     * Valor da compra
     */
    private BigDecimal valorDaCompra;

    /**
     * Para pix saque e pix troco esse campo representa o valor em espécie para devolução
     */
    private BigDecimal valorEmEspecieDisponivelDevolucao;
    private BigDecimal multa;
    private BigDecimal juros;
    private BigDecimal desconto;

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
