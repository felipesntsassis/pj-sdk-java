package inter.banking.model;

import inter.banking.model.enums.TipoOperacao;
import inter.banking.model.enums.TipoTransacao;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class FiltroConsultarExtratoEnriquecido {

    private TipoOperacao tipoOperacao;
    private TipoTransacao tipoTransacao;

    public String obterFiltroQuery() {
        StringBuilder filter = new StringBuilder();

        if (getTipoOperacao() != null) {
            filter.append("&tipoOperacao").append("=").append(getTipoOperacao().toString());
        }

        if (getTipoTransacao() != null) {
            filter.append("&tipoTransacao").append("=").append(getTipoTransacao().toString());
        }

        return filter.toString();
    }
}
