package inter.pix.model;

import com.fasterxml.jackson.annotation.JsonInclude;
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
public class Callback {

    private String webhookUrl;
    private Object payload;
    private Integer numeroTentativa;
    private Boolean sucesso;
    private Integer httpStatus;
    private String mensagemErro;
    private String dataHoraDisparo;

}
