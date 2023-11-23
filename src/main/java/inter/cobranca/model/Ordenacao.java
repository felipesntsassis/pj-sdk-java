package inter.cobranca.model;

import inter.cobranca.model.enums.OrdenadoPor;
import inter.cobranca.model.enums.TipoOrdenacao;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Ordenacao {

    private OrdenadoPor ordenarPor;
    private TipoOrdenacao tipoOrdenacao;

}
