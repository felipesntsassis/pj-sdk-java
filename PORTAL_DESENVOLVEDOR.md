## Pré-requisitos

### Java
Versão 8 ou superior.

### Download e configuração do InterSdk

[Clique aqui](https://developers.inter.co/media/inter-sdk-java.jar) para baixar o InterSdk Java.

Para utilizá-lo, você deve adicionar o arquivo _inter-sdk-java.jar_ ao classpath da sua aplicação. Verifique na documentação da sua IDE, como adicionar uma dependência externa.

Como exemplo, no IntelliJ, siga as seguintes instruções:

* No menu do IntelliJ, selecione a opção _File > Project Structure_.
* Vá até a opção _Modules_ e em seguida selecione a aba _Dependencies_.
* Clique no botão _"+"_ e selecione a opção _JARs or Directories_.
* Selecione o _inter-sdk-java.jar_.

Se você compila sua aplicação pela linha de comando, você pode adicionar o _inter-sdk-java.jar_ na variável de ambiente CLASSPATH, ou incluir a opção "-cp path" na linha de comando, onde path é o caminho para o arquivo .jar baixado.

### Download e configuração do certificado digital
Para baixar o seu certificado, siga as instruções desse documento: https://developers.bancointer.com.br/v4/docs/onde-obter-o-certificado

Verifique se possui o openssl instalado em sua maquina com o seguinte comando no terminal: openssl version.
Caso não tenha instalado, instale o openssl. Ele será necessário para gerar o arquivo do certificado no formato utilizado pelo Java (.pfx).

- Baixar OpenSSL para MAC: https://www.macupdate.com/app/mac/62162/openssl
- Baixar OpenSSL para Windows: https://slproweb.com/products/Win32OpenSSL.html
- Baixar OpenSSL para Linux: https://help.dreamhost.com/hc/en-us/articles/360001435926-Installing-OpenSSL-locally-under-your-username

Gere o arquivo .pfx (pkcs12) com o seguinte comando:

```
openssl pkcs12 -export -out NOME_DO_ARQUIVO.pfx -inkey "CHAVE.key" -in "CERTIFICADO.crt" -legacy -password pass:SENHA
```

Onde:
+ NOME_DO_ARQUIVO = Nome do arquivo .pfx que será gerado (deverá ser informado na inicialização do InterSdk).
+ CHAVE = Nome do arquivo .key baixado no portal do desenvolvedor.
+ CERTIFICADO = Nome do arquivo .crt baixado no portal do desenvolvedor.
+ SENHA = Senha para o arquivo .pfx (deverá ser informada na inicialização do InterSdk).

Exemplo:

```
openssl pkcs12 -export -out inter.pfx -inkey "Inter API_Chave.key" -in "Inter API_Certificado.crt" -legacy -password pass:intersdk
```

OBS: Dependendo da versão do openssl, pode ser que a opção -legacy cause um erro na execução. Nesse caso execute novamente sem essa opção.

Para usar o certificado (arquivo .pfx), basta colocá-lo dentro do projeto da sua aplicação. Esse passo não é obrigatório mas poderá simplificar a inicialização do InterSdk.

## Como utilizar e recursos do InterSdk

### Inicialização
Antes de executar os comandos disponibilizados pelo InterSdk, é necessário inicializa-lo. Para isso, basta passar os seguintes parâmetros no construtor:

```java
InterSdk interSdk = new InterSdk(
    "CLIENT_ID", // Pode ser obtido no detalhe da tela de aplicações do Internet Banking
    "CLIENT_SECRET", // Pode ser obtido no detalhe da tela de aplicações no Internet Banking
    "CAMINHO_ARQUIVO_PFX", // Caminho do arquivo .pfx gerado (utilizando o openssl)
    "SENHA_ARQUIVO_PFX" // Senha utilizada na geração do certificado .pfx
);

// EXEMPLO:

InterSdk interSdk = new InterSdk(
    "2179676f-3069-44de-96c3-07739bcded35",
    "29f8fe86-efab-4b31-a5f6-6d22ebcb2014",
    "src/resources/inter.pfx",
    "intersdk"
);
```

### Tratamento de erros
Em caso de erros, todos os métodos do InterSdk lançam exceções. Dessa forma, se a chamada do método passar sem exceções, pode-se considerar que foi uma chamada com sucesso.

As exceções podem ser separadas em dois grupos: _SdkException_ e _outras exceções_.

Todas as exceções descendentes da SdkException terão um objeto de erro. Esse objeto terá título, detalhe, data e uma lista de violações:

```java
public class Erro {
    private String title;
    private String detail;
    private Date timestamp;
    private List<Violacao> violacoes;
    ...
}
 
public class Violacao {
    private String razao;
    private String propriedade;
    private String valor;
    ...
}
```

Exemplo de tratamento de erros:
```java
public class TratamentoErros {
    public void exemplo(InterSdk interSdk) {
        try {
            // Executa um método do InterSdk
            Saldo saldo = interSdk.banking().consultarSaldo();
            
            // Método executado com sucesso
            System.out.println(saldo.getDisponivel());
        } catch (SdkException e) {
            // Pode ser ClientException ou ServerException
            System.out.println(e.getClass().getName());
            
            // Retorna uma lista de erros com título, detalhe, data e uma lista de violações
            System.out.println(e.getErro().getTitle());  //Ex: A presente requisicao nao respeita o schema ou não faz sentido semanticamente.
            System.out.println(e.getErro().getDetail()); //Ex: Requisicao invalida.
            
            for (Violacao violacao : e.getErro().getViolacoes()) {
                System.out.println(violacao.getRazao()); // Ex: Periodo maximo entre as datas é de 90 dias.
                System.out.println(violacao.getPropriedade()); // Ex: Data inicio, Data Fim
                System.out.println(violacao.getValor());
            }
            
            e.printStackTrace();
        } catch (Exception e) {
            // Trata outras exceções
            e.printStackTrace();
        }
    }
}
```

### Múltiplas contas
Caso sua aplicação seja associada a mais de uma conta, você poderá a qualquer momento mudar e consultar a conta que será utilizada pelo InterSdk.

```java
public void exemplo(String clientId, String clientSecret, String certificado, String senha) {
    InterSdk interSdk = new InterSdk(clientId, clientSecret, certificado, senha);
    
    // Seta a conta
    interSdk.setContaCorrente("123456");
    // Executa comando na conta selecionada
    Saldo saldo = interSdk.banking().consultarSaldo();
    
    // Seta outra conta
    interSdk.setContaCorrente("098765");
    // Executa comando na nova conta selecionada
    Extrato extrato = interSdk.banking().consultarExtrato("2023-01-01", "2023-01-15");    
}
```

### Geração de logs
O InterSdk grava logs dos comandos executados, com o objetivo de auxiliar na identificação de possíveis erros.

Os arquivos de log ficam armazenados na pasta "logs", dentro do diretório de execução da aplicação. Se a pasta "logs" não existir ela será criada automaticamente.

Será criado um arquivo de log para cada dia da semana.  O log do dia seguinte ao dia atual será sempre removido no primeiro acesso do dia, sendo assim, a pasta poderá ter até 6 arquivos.

Ex.: inter-sdk-Seg.log, inter-sdk-Ter.log

### Modo debug
O modo debug por padrão é desabilitado.

Se o modo debug estiver habilitado, os logs gerados conterão as requisições e as respostas das chamadas das APIs do Inter que são feitas internamente pelo InterSdk.

Para habilitar/desabilitar basta executar o seguinte método:

```java
interSdk.setDebug(true);
```

### Controle de rate limit
Os endpoints das APIs do Inter tem um limite máximo de chamadas permitidas por minuto (rate limit).

Se esse limite for ultrapassado, o InterSdk vai retornar o seguinte erro:
```
title="HTTP/1.1 429 Too Many Requests"
```

O SDK, por padrão, trata automaticamente esse erro. Caso o rate limit seta ultrapassado, o SDK vai fazer uma pausa de um minuto e vai tentar novamente.

Para desativar essa funcionalidade utilize o seguinte comando:
```java
interSdk.setControleRateLimit(false);
```


### Paginação
Algumas operações retornam uma lista de resultados, como por exemplo, a consulta de extrato enriquecido.

Para essas operações, o InterSdk terá 3 métodos diferentes, um com paginação e com tamanho de página padrão, um com paginação e tamanho de página especificado e um sem paginação.

No caso do método sem paginação, a aplicação só precisa chamar o método uma vez, e essa chamada vai retornar o resultado completo.

No caso dos métodos com paginação, eles vão retornar um objeto contendo as informações necessárias para recuperar as próximas páginas, além de uma página de resultados. A aplicação deverá então verificar se tem mais páginas para ler, e assim poderá chamar o método novamente para pegar a próxima página. A cada chamada, a aplicação deverá incrementar o parâmetro "pagina". Na primeira chamada esse parâmetro deverá ter o valor "0".

Exemplo de chamada sem paginação:
```java
List<TransacaoEnriquecida> transacoes = interSdk.banking().consultarExtratoEnriquecido(dataInicial, dataFinal, filtro);
```
Exemplo de chamada com paginação:
```java
int pagina = 0;

ExtratoEnriquecido extrato;

do {
    // Nesse caso está especificando o tamanho de página = 10, para a consulta de extrato o padrão é 50.
    extrato = interSdk.banking().consultarExtratoEnriquecido("2023-01-01", "2023-01-05", null, pagina, 10);
    System.out.println("Quantidade páginas: " + extrato.getTotalPaginas());

    // Exibe todas as transações da página
    extrato.getTransacoes().forEach(transacaoEnriquecida -> System.out.println("Transação: " + transacaoEnriquecida.getIdTransacao()));
    
    // Incrementa a página para a próxima consulta
    pagina++;
    System.out.println("Página: " + pagina);
} while (pagina < extrato.getTotalPaginas());
```

### Avisos
O InterSdk tem o recurso de retornar avisos para o usuário.
Os avisos tem o objetivo de prever problemas futuros, por exemplo, se o certificado digital estiver próximo de expirar, o InterSdk vai gerar um aviso para isso.
```java
InterSdk interSdk = new InterSdk(clientId, clientSecret, certificado, senha);
interSdk.listaAvisos().forEach(System.out::println);
```

### Classes modelo
O InterSdk oferece classes de modelo para todos os dados enviados e recebidos.

Com essas classes fica fácil de saber quais dados e quais tipos de dados são utilizados em cada método.

Por exemplo, a consulta de saldo retorna um objeto do tipo Saldo:
```java
public class Saldo {
    private BigDecimal disponivel;
    private BigDecimal bloqueadoCheque;
    private BigDecimal bloqueadoJudicialmente;
    private BigDecimal bloqueadoAdministrativo;
    private BigDecimal limite;
    ...
}

System.out.printf("Saldo disponível: %.2f%n", saldo.getDisponivel());
```
Da mesma forma, ao emitir um boleto, precisamos enviar um objeto do tipo Boleto, que por sua vez, contém outros objetos, como o pagador e o beneficiário final que são do tipo Pessoa:
```java
RespostaEmitirBoleto resposta = interSdk.cobranca().emitirBoleto(boleto);

public class Boleto {
    /**
     * REQUERIDO
     * <p><Campo Seu Número do título/p>
     */
    private String seuNumero;
    /**
     * REQUERIDO
     * <p>Valor Nominal do título/p>
     * <p>O valor nominal deve ser maior ou igual à R$2.50/p>
     */
    private BigDecimal valorNominal;
    /**
     * Valor de abatimento do título, expresso na mesma moeda do Valor Nominal
     */
    private BigDecimal valorAbatimento;
    /**
     * REQUERIDO
     * <p>Data de vencimento do título/p>
     * <p>Formato aceito: YYYY-MM-DD/p>
     */
    private String dataVencimento;
    /**
     * REQUERIDO
     * <p>Número de dias corridos após o vencimento para o cancelamento efetivo automático do boleto. (de 0 até 60)/p>
     */
    private Integer numDiasAgenda;
    /**
     * REQUERIDO
     */
    private Pessoa pagador;
    private Mensagem mensagem;
    private Desconto desconto1;
    private Desconto desconto2;
    private Desconto desconto3;
    private Multa multa;
    private Mora mora;
    private Pessoa beneficiarioFinal;

    private Map<String, Object> camposAdicionais = new HashMap<>();
    ...
}
```

Para alguns campos, que possuem um domínio definido, o InterSdk os define como enums. Exemplo:
```java
.tipoPessoa(TipoPessoa.FISICA)

public enum TipoPessoa {
    FISICA, JURIDICA
}
```

Todas as classes, implementam o padrão Builder, facilitando a criação de objetos. Exemplo:
```java
Boleto boleto = Boleto.builder()
        .seuNumero(seuNumero)
        .valorNominal(valor)            // O valor nominal deve ser maior ou igual à R$2.50
        .dataVencimento(dataVencimento) // Formato aceito: YYYY-MM-DD
        .numDiasAgenda(0)               // Número de dias corridos após o vencimento para o cancelamento efetivo automático do boleto. (de 0 até 60)
        .pagador(pagador)
        .build();
```

**IMPORTANTE! Algumas classes possuem o atributo camposAdicionais. Esse atributo é um mapa que pode conter dados que ainda não foram mapeados no InterSdk que você está utilizando. Ou seja, ele é importante caso tenha ocorrido alguma atualização nas APIs mas seu InterSdk esteja desatualizado, você pode pegar as informações que precisa através desse atributo.**

### Javadoc
Todos os métodos do InterSdk são documentados com javadoc.

Com a documentação javadoc, você pode ter acesso a informações detalhadas sobre o método apenas posicionando o mouse sobre ele (ver documentação da sua IDE).

![Exemplo javadoc](images/javadoc.png)

## Projeto exemplo

### Código e documentação dos exemplos de uso do InterSdk

Disponibilizamos um projeto de exemplo nesse link: https://developers.inter.co/media/demo-sdk-java.zip. O objetivo dele é mostrar as funcionalidades do InterSdk, bem como a utilização e execução dos seus modelos e métodos.

Durante a execução dessa aplicação, algumas informações serão solicitadas e outra menos relevantes estão fixas no código.

### Aplicação demo

Caso você queira baixar apenas a aplicação demo, basta acessar aqui: https://developers.inter.co/media/demo-sdk-java.jar

Para rodar esse projeto, execute o seguinte comando no terminal:
```
java -cp demo-sdk-java.jar inter.sdk.demo.App
```
