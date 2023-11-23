package inter.cobranca.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;
import lombok.experimental.SuperBuilder;

@SuperBuilder
@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class FiltroRecuperarBoletos extends FiltroBaseRecuperarBoletos {

    /**
     * Filtro pelo campo nossoNumero da cobrança
     */
    private String nossoNumero;

}
