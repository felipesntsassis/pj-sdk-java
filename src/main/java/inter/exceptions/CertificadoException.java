package inter.exceptions;

import inter.model.Erro;

public class CertificadoException extends SdkException {
    public CertificadoException(String mensagem, Erro erro) {
        super(mensagem, erro);
    }
}
