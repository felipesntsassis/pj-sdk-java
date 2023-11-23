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
public class InstituicaoFinanceira {

    /**
     * Código do banco
     */
    private String codigo;

    /**
     * Nome do banco
     */
    private String nome;

    /**
     * Código ISPB, de 8 dígitos, dos bancos
     */
    private String ispb;

    /**
     * Tipos de pagamentos que podem ser realizados. Por chave pix ou por dados bancários.
     */
    private String tipo;

}
