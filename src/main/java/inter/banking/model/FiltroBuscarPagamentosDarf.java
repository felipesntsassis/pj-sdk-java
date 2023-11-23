package inter.banking.model;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonInclude;
import inter.banking.model.enums.TipoDataPagamentoDarf;
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
public class FiltroBuscarPagamentosDarf {

    private String codigoTransacao;
    private String codigoReceita;
    private TipoDataPagamentoDarf filtrarDataPor;

    private Map<String, Object> camposAdicionais = new HashMap<>();

    @JsonAnyGetter
    public Map<String, Object> getCamposAdicionais() {
        return camposAdicionais;
    }

    @JsonAnySetter
    public void setCamposAdicionais(final String nome, final Object valor) {
        this.camposAdicionais.put(nome, valor);
    }

    public String obterFiltroQuery() {
        StringBuilder filter = new StringBuilder();

        if (getCodigoTransacao() != null) {
            filter.append("&codigoTransacao").append("=").append(getCodigoTransacao());
        }

        if (getCodigoReceita() != null) {
            filter.append("&codigoReceita").append("=").append(getCodigoReceita());
        }

        if (getFiltrarDataPor() != null) {
            filter.append("&filtrarDataPor").append("=").append(getFiltrarDataPor().toString());
        }

        camposAdicionais.forEach((k,v)->filter.append("&").append(k).append("=").append(v.toString()));

        return filter.toString();
    }

}
