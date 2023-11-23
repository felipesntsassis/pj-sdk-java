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
public class Recebedor {

    private String nome;
    private String cnpj;
    private String nomeFantasia;
    private String cidade;
    private String uf;
    private String cep;
    private String logradouro;

}
