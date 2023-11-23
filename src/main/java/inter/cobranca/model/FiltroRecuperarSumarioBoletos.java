package inter.cobranca.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.experimental.SuperBuilder;

@SuperBuilder
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class FiltroRecuperarSumarioBoletos extends FiltroBaseRecuperarBoletos {
}
