package inter.banking.model;

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
public class FiltroBuscarCallbacks {

    private String endToEnd;

    public String obterFiltroQuery() {
        StringBuilder filter = new StringBuilder();

        if (getEndToEnd() != null) {
            filter.append("&endToEnd").append("=").append(getEndToEnd());
        }

        return filter.toString();
    }
}
