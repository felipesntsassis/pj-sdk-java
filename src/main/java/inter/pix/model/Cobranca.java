package inter.pix.model;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@SuperBuilder
@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Cobranca {

    /**
     * REQUERIDO
     * O campo chave determina a chave Pix do recebedor que será utilizada para a cobrança.
     * Os tipos de chave podem ser: telefone, e-mail, cpf/cnpj ou EVP.
     * O formato das chaves pode ser encontrado na seção "Formatação das chaves do DICT no BR Code" do Manual de Padrões para iniciação do Pix.
     */
    private String chave;

    /**
     * O campo solicitacaoPagador determina um texto a ser apresentado ao pagador para que ele possa digitar uma informação correlata,
     * em formato livre, a ser enviada ao recebedor. Esse texto está limitado a 140 caracteres.
     */
    private String solicitacaoPagador;

    /**
     * Cada respectiva informação adicional contida na lista (nome e valor) deve ser apresentada ao pagador.
     */
    private List<InfoAdicional> infoAdicionais;

    private Devedor devedor;

    /**
     * Identificador da localização do payload.
     */
    private Location loc;

    /**
     * REQUERIDO
     * valores monetários referentes à cobrança.
     */
    private Valor valor;

    /**
     * REQUERIDO
     */
    private Calendario calendario;

    private String txid;

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
