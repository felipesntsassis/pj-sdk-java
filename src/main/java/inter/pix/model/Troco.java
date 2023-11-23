package inter.pix.model;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import inter.pix.model.enums.ModalidadeAgente;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashMap;
import java.util.Map;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Troco {

    /**
     * REQUERIDO
     * Valor do troco efetuado
     */
    private String valor;

    /**
     * Modalidade de alteração de valor do saque/troco. Quando não preenchido o valor assumido é o 0 (zero).
     */
    private Integer modalidadeAlteracao;

    /**
     * REQUERIDO
     */
    private ModalidadeAgente modalidadeAgente;

    /**
     * REQUERIDO
     * SPB do Facilitador de Serviço de Saque
     */
    private String prestadorDoServicoDeSaque;

    private Map<String, Object> camposAdicionais = new HashMap<>();

    @JsonAnyGetter
    public Map<String, Object> getCamposAdicionais() {
        return camposAdicionais;
    }

    @JsonAnySetter
    public void setCamposAdicionais(final String nome, final Object valor) {
        this.camposAdicionais.put(nome, valor);
    }

}
