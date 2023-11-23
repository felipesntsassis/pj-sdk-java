package inter.pix.model;

import inter.pix.model.enums.NaturezaDevolucao;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Devolucao {

    /**
     * Valor solicitado para devolução. A soma dos valores de todas as devolucões não podem ultrapassar o valor total do Pix.
     * <p>REQUERIDO</p>
     */
    private String valor;

    /**
     * Indica qual é a natureza da devolução solicitada. Uma solicitação de devolução pelo usuário recebedor pode ser relacionada a um Pix
     * comum (com código: MD06 da pacs.004), ou a um Pix de Saque ou Troco (com códigos possíveis: MD06 e SL02 da pacs.004). Na ausência
     * deste campo a natureza deve ser interpretada como sendo de um Pix comum (ORIGINAL).
     * <p>As naturezas são assim definidas:</p>
     * <ul>
     *     <li>ORIGINAL: quando a devolução é solicitada pelo usuário recebedor e se refere a um Pix comum ou ao valor da compra em um Pix Troco (MD06);</li>
     *     <li>RETIRADA: quando a devolução é solicitada pelo usuário recebedor e se refere a um Pix Saque ou ao valor do troco em um Pix Troco (SL02).</li>
     * </ul>
     * <p>Os valores de devoluções são sempre limitados aos valores máximos a seguir:</p>
     * <ul>
     *     <li>Pix comum: o valor da devolução é limitado ao valor do próprio Pix (a natureza nesse caso deve ser: ORIGINAL);</li>
     *     <li>Pix Saque: o valor da devolução é limitado ao valor da retirada (a natureza nesse caso deve ser: RETIRADA); e</li>
     *     <li>
     *         Pix Troco: o valor da devolução é limitado ao valor relativo à compra ou ao troco:
     *          <ul>
     *              <li>Quando a devolução for referente à compra, o valor limita-se ao valor da compra (a natureza nesse caso deve ser ORIGINAL); e</li>
     *              <li>Quando a devolução for referente ao troco, o valor limita-se ao valor do troco (a natureza nesse caso deve ser RETIRADA).</li>
     *          </ul>
     *     </li>
     * </ul>
     */
    private NaturezaDevolucao natureza;

    /**
     * O campo descricao, opcional, determina um texto a ser apresentado ao pagador contendo informações sobre a devolução. Esse texto será preenchido, na pacs.004, pelo PSP do recebedor, no campo RemittanceInformation. O tamanho do campo na pacs.004 está limitado a 140 caracteres.
     */
    private String descricao;

}
