package inter.pix.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import inter.pix.model.enums.StatusCobranca;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.List;

@SuperBuilder
@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CobrancaVencimentoDetalhada extends CobrancaVencimento{

    private String pixCopiaECola;
    private Recebedor recebedor;
    private StatusCobranca status;
    private Integer revisao;
    private List<Pix> pix;

}
