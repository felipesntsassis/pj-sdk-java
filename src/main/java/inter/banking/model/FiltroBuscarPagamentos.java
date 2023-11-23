package inter.banking.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import inter.banking.model.enums.TipoDataPagamento;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class FiltroBuscarPagamentos {

    private String codBarraLinhaDigitavel;
    private String codigoTransacao;
    private TipoDataPagamento filtrarDataPor;

    public String obterFiltroQuery () {
        StringBuilder filter = new StringBuilder();

        if (getCodBarraLinhaDigitavel() != null) {
            filter.append("&codBarraLinhaDigitavel").append("=").append(getCodBarraLinhaDigitavel());
        }

        if (getCodigoTransacao() != null) {
            filter.append("&codigoTransacao").append("=").append(getCodigoTransacao());
        }

        if (getFiltrarDataPor() != null) {
            filter.append("&filtrarDataPor").append("=").append(getFiltrarDataPor().toString());
        }

        return filter.toString();
    }

}
