package inter.pix.model;

import inter.pix.model.enums.TipoCob;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CriarLocationRequest {

    private TipoCob tipoCob;

}
