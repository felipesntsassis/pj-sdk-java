package inter.pix.model;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonInclude;
import inter.pix.model.enums.TipoCob;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Location {

    private TipoCob tipoCob;

    /**
     * Identificador da location a ser informada na criação da cobrança .
     */
    private Long id;

    /**
     * Localização do Payload a ser informada na criação da cobrança.
     */
    private String location;

    /**
     * Data e hora em que a location foi criada. Respeita RFC 3339.
     */
    private Date criacao;

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
