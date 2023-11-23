package inter.cobranca.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PaginaCallbacks {

    /**
     * Quantidade total de páginas disponíveis para consulta.
     */
    @JsonProperty(value = "totalPaginas")
    private Integer totalPaginas;

    /**
     * Quantidade total de itens disponíveis de acordo com os parâmetros informados.
     */
    @JsonProperty(value = "totalElementos")
    private Integer totalElementos;

    /**
     * Última página
     */
    @JsonProperty(value = "ultimaPagina")
    private Boolean ultimaPagina;

    /**
     * Primeira página
     */
    @JsonProperty(value = "primeiraPagina")
    private Boolean primeiraPagina;

    @JsonProperty(value = "data")
    private List<Callback> callbacks;

}
