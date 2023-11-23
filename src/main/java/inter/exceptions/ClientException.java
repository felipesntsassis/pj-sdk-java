package inter.exceptions;

import inter.model.Erro;

public class ClientException extends SdkException {
    public ClientException(String mensagem, Erro erro) {
        super(mensagem, erro);
    }
}
