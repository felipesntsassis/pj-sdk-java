package inter.banking.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Favorecido implements Destinatario {

    /**
     * Id do favorecido
     */
    private String id;

    /**
     * Nome do favorecido
     * <p>REQUERIDO</p>
     */
    private String nome;

    /**
     * CPF/CNPJ do favorecido
     */
    private String cpfCnpj;

    /**
     * REQUERIDO
     */
    private InstituicaoFinanceira instituicaoFinanceira;

    /**
     * Código da agência
     * <p>REQUERIDO</p>
     */
    private String agencia;

    /**
     * Número da conta
     * <p>REQUERIDO</p>
     */
    private String conta;

    /**
     * Email do favorecido
     */
    private String email;

    /**
     * Descricao para o favorecido
     */
    private String descricao;

}
