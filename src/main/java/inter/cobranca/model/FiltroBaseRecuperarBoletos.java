package inter.cobranca.model;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import inter.cobranca.model.enums.SituacaoBoleto;
import inter.cobranca.model.enums.TipoDataBoleto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.HashMap;
import java.util.Map;

@SuperBuilder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class FiltroBaseRecuperarBoletos {

    /**
     * Caso o campo situacao seja:
     * <ul>
     *     <li>EXPIRADO as datas corresponderão a data de expiração dos boletos;</li>
     *     <li>VENCIDO(default) as datas corresponderão a data de vencimento dos boletos;</li>
     *     <li>EMABERTO as datas corresponderão a data de emissão dos boletos;</li>
     *     <li>PAGO as datas corresponderão a data de pagamento dos boletos;</li>
     *     <li>CANCELADO as datas corresponderão a data de cancelamento dos boletos;</li>
     * </ul>
     */
    private TipoDataBoleto filtrarDataPor;

    /**
     * Filtro pela situação da cobrança.
     */
    private SituacaoBoleto situacao;

    /**
     * Filtro pelo nome do pagador
     */
    private String nome;

    /**
     * Filtro pelo email do pagador
     */
    private String email;

    /**
     * Campo para informar o cpf ou cnpj do pagador
     */
    private String cpfCnpj;

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
