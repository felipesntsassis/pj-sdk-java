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
public class RespostaIncluirPagamento {

    private Integer quantidadeAprovadores;
    private String dataAgendamento;

    /**
     * REALIZADO AGENDADO AGUARDANDO_APROVACAO APROVADO AGENDADO_REALIZADO
     */
    private String statusPagamento;
    private String codigoTransacao;

}
