package inter.exceptions;

import inter.model.Erro;

import java.util.Date;

import static inter.constants.Constants.DOC_CERTIFICADO;

public class CertificadoExpiradoException extends ClientException {
    public CertificadoExpiradoException(Date notAfter) {
        super("Certificado expirado", Erro.builder()
                .title("Certificado expirado")
                .detail(String.format("Certificado expirado em %s. Consulte %s.", notAfter, DOC_CERTIFICADO))
                .build());
    }
}
