package inter.pix.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.List;

@SuperBuilder
@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CobrancaDetalhada extends Cobranca {

    /**
     * Este campo retorna o valor do Pix Copia e Cola correspondente à cobrança. Trata-se da sequência de caracteres que representa o BR Code.
     */
    private String pixCopiaECola;

    private String location;

    /**
     * ATIVA CONCLUIDA REMOVIDA_PELO_USUARIO_RECEBEDOR REMOVIDA_PELO_PSP
     */
    private String status;

    /**
     * Denota a revisão da cobrança. Sempre começa em zero. Sempre varia em acréscimos de 1.
     * O incremento em uma cobrança deve ocorrer sempre que um objeto da cobrança em questão for alterado.
     * O campo loc é uma exceção a esta regra.
     * Se em uma determinada alteração em uma cobrança, o único campo alterado for o campo loc,
     * então esta operação não incrementa a revisão da cobrança.
     */
    private Integer revisao;

    private List<Pix> pix;

}
