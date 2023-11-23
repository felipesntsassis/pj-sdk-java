package inter.pix.model;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@SuperBuilder
@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CobrancaVencimento {

    private String chave;
    private String solicitacaoPagador;
    private List<InfoAdicional> infoAdicionais;
    private Devedor devedor;
    private Location loc;
    private CobVValor valor;
    private CalendarioCobV calendario;
    private String txid;

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
