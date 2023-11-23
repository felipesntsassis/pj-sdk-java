package inter.cobranca.model;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PaginaBoletos {

    /**
     * Quantidade total de páginas disponíveis para consulta.
     */
    @JsonProperty(value = "totalPages")
    private Integer totalPaginas;

    /**
     * Quantidade total de itens disponíveis de acordo com os parâmetros informados.
     */
    @JsonProperty(value = "totalElements")
    private Integer totalElementos;

    /**
     * Última página
     */
    @JsonProperty(value = "last")
    private Boolean ultimaPagina;

    /**
     * Primeira página
     */
    @JsonProperty(value = "first")
    private Boolean primeiraPagina;

    /**
     * Quantidade de registros por página, configurado na requisição.
     */
    @JsonProperty(value = "size")
    private Integer tamanhoPagina;

    /**
     * Quantidade de registros retornado na página atual.
     */
    @JsonProperty(value = "numberOfElements")
    private Integer numeroDeElementos;
    @JsonProperty(value = "content")
    private List<BoletoDetalhado> boletos;

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
        return totalPaginas;
    }

}
