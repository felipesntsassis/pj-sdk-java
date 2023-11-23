package inter.exceptions;

import inter.model.Erro;

public class ServerException extends SdkException {
    public ServerException(String mensagem, Erro erro) {
        super(mensagem, erro);
    }
}
