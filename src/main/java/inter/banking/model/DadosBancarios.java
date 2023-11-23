package inter.banking.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import inter.banking.model.enums.TipoConta;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class DadosBancarios implements Destinatario {

    private final String tipo = "DADOS_BANCARIOS";

    private String contaCorrente;
    private TipoConta tipoConta;
    private String cpfCnpj;
    private String agencia;
    private String nome;
    private InstituicaoFinanceira instituicaoFinanceira;

}
