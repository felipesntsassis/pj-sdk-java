package inter.cobranca.model;

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
public class RespostaEmitirBoleto {

    /**
     * Seu Número, enviado na requisição para inclusão do título.
     */
    private String seuNumero;

    /**
     * Nosso Número, atribuído automaticamente pelo banco na emissão do título.
     */
    private String nossoNumero;

    /**
     * Representação numérica do código de barras do título emitido. (44 dígitos)
     */
    private String codigoBarras ;

    /**
     * Contém os mesmos dados do código de barras dispostos em ordem diferente e acrescido de dígitos verificadores. (47 dígitos)
     */
    private String linhaDigitavel;

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
