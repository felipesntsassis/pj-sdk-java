package inter.pix.model;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PaginaCobrancasVencimento {

    private Parametros parametros;
    @JsonProperty(value = "cobs")
    private List<CobrancaVencimentoDetalhada> cobrancas;
    private Map<String, Object> camposAdicionais = new HashMap<>();

    @JsonAnyGetter
    public Map<String, Object> getCamposAdicionais() {
        return camposAdicionais;
    }

    @JsonAnySetter
    public void setCamposAdicionais(final String nome, final Object valor) {
        this.camposAdicionais.put(nome, valor);
    }

    public int getQuantidadeDePaginas() {
        if (parametros == null || parametros.getPaginacao() == null) {
            return 0;
        }
        return parametros.getPaginacao().getQuantidadeDePaginas();
    }

}
