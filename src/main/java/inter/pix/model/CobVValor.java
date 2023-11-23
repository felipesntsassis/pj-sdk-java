package inter.pix.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CobVValor {

    private String original;
    private Multa multa;
    private Juros juros;
    private Abatimento abatimento;
    private Desconto desconto;

}
