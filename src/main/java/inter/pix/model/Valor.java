package inter.pix.model;

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
public class Valor {

    /**
     * REQUERIDO
     * Valor original da cobrança.
     */
    private String original;

    /**
     * Trata-se de um campo que determina se o valor final do documento pode ser alterado pelo pagador.
     * Na ausência desse campo, assume-se que não se pode alterar o valor do documento de cobrança, ou seja, assume-se o valor 0.
     * Se o campo estiver presente e com valor 1, então está determinado que o valor final da cobrança pode ter seu valor alterado pelo pagador.
     */
    private Integer modalidadeAlteracao;

    /**
     * É uma estrutura opcional relacionada ao conceito de recebimento de numerário. Apenas um agrupamento por vez é permitido, quando há saque não há troco e vice-versa.
     * Quando uma cobrança imediata tem uma estrutura de retirada ela deixa de ser considerada Pix comum e passa à categoria de Pix Saque ou Pix Troco.
     * <p>Para que o preenchimento do objeto retirada seja considerado válido as seguintes regras se aplicam:
     * <ul>
     *     <li>os campos modalidadeAgente e prestadorDoServicoDeSaque são de preenchimento obrigatório;</li>
     *     <li>quando o saque estiver presente a cobrança deve respeitar as seguintes condições:</li>
     *     <ul>
     *         <li>O campo valor.original deve ser preenchido com valor igual a 0.00 (zero);</li>
     *         <li>O campo valor.modalidadeAlteracao deve possuir o valor 0 (zero) explicitamente, ou implicitamente (pelo não preenchimento).</li>
     *     </ul>
     *     <li>quando o troco estiver presente a cobrança deve respeitar as seguintes condições</li>
     *     <ul>
     *         <li>O campo valor.original deve ser preenchido com valor maior que 0.00 (zero);</li>
     *         <li>O campo valor.modalidadeAlteracao deve possuir o valor 0 (zero) explicitamente, ou implicitamente (pelo não preenchimento).</li>
     *     </ul>
     * </ul>
     * </p>
     * <p><b>IMPORTANTE:</b> Quando usados o saque ou troco não será permitida a alteração do valor.original recebido. Na presença de saque ou troco o recebimento do campo valor.modalidadeAlteracao com valor 1 (um) é considerado erro.</p>
     */
    private Retirada retirada;

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
