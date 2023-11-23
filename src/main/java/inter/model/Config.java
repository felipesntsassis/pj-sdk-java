package inter.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Builder
public class Config {
    @Setter
    private String ambiente;
    private final String clientId;
    private final String clientSecret;
    private final String certificado;
    private final String senha;
    @Setter
    private boolean debug;
    @Setter
    private String contaCorrente;
    @Setter
    private boolean controleRateLimit;
}
