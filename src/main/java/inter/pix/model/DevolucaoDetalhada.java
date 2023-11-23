package inter.pix.model;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonInclude;
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
@JsonInclude(JsonInclude.Include.NON_NULL)
public class DevolucaoDetalhada {

    /**
     * Id gerado pelo cliente para representar unicamente uma devolução.
     */
    private String id;

    /**
     * ReturnIdentification que transita na PACS004.
     */
    private String rtrId;

    /**
     * Valor a devolver.
     */
    private String valor;

    /**
     * Status da devolução.
     * <ul>
     *     <li>EM_PROCESSAMENTO</li>
     *     <li>DEVOLVIDO</li>
     *     <li>NAO_REALIZADO</li>
     * </ul>
     */
    private String status;

    /**
     * Campo opcional que pode ser utilizado pelo PSP recebedor para detalhar os motivos
     * de a devolução ter atingido o status em questão.
     * Pode ser utilizado, por exemplo, para detalhar o motivo de a devolução não ter sido realizada.
     */
    private String motivo;

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
