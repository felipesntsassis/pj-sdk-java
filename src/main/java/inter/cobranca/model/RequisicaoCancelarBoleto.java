package inter.cobranca.model;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import inter.cobranca.model.enums.MotivoCancelamento;
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
public class RequisicaoCancelarBoleto {

    /**
     * Domínio que descreve o tipo de cancelamento sendo solicitado.
     */
    private MotivoCancelamento motivoCancelamento;

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
