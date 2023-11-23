package inter.exceptions;

import inter.model.Erro;

import static inter.constants.Constants.DOC_CERTIFICADO;

public class CertificadoNaoEncontradoException extends ClientException {
    public CertificadoNaoEncontradoException(String certificado) {
        super("Certificado não encontrado", Erro.builder()
                .title("Certificado não encontrado")
                .detail(String.format("Certificado não encontrado: %s. Consulte %s.", certificado, DOC_CERTIFICADO))
                .build());
    }
}
