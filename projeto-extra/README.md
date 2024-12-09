# Projeto Extra - Configurando Sistema e Tomcat com Docker

#### Como projeto extra, escolhi utilizar um sistema que desenvolvi com Java e Thymeleaf para a loja da minha mãe. Então irei configurar o sistema para utilizar o servidor de aplicação Tomcat, que será configurado com o Docker. 

#### Utilizarei o banco de dados H2 para fins de teste, já que ele é leve e de fácil configuração.

#### Como já temos instalado em nossa máquina o Docker, que instalamos anteriormente via script inicial, nós iremos seguir alguns passos para configurar tudo.

- Fazer o clone do projeto.
- Criar um Dockerfile do Tomcat.
- Fazer o build.
- Executar a imagem com o comando ` docker run `.
- Verificar se está tudo funcionando.

## 1. Fazer o clone do repositório do github para a máquina local.

    $ git clone https://github.com/Paulo-Fabiano/api-crud.git

### Caso queira pode acessar o reposítorio pelo link abaixo e verificar.

- [Link do Repositório](https://github.com/Paulo-Fabiano/api-crud.git)

## 2 Verificar se o projeto está configurado para gerar um arquivo `.war`. Como ele não está configurado iremos fazer isso no arquivo `pom.xml`.

#### Faremos a adição da seguinte linha no arquivo.
    <packaging>war</packaging>

## Criaremos uma classe também

### Devemos nos certificar de ter a JDK (Java Development Kit ou Kit de Desenvolvimento Java) e o Maven para gerar o war do projeto.

#### Caso não tenha, iremos instalar a JDK e o Maven via terminal

    $ sudo apt install openjdk-17-jdk -y

    $ sudo apt install maven -y

#### Verifique se a JDK e o Maven estão instalados corretamente

    $ java --version

    $ mvn -v 

#### Com tudo configurado iremos partir para as próximas etapas.

## 3 Usamos um comando para gerar um arquivo `war` do projeto. Dentro do diretório que está o `pom.xml` dê o comando:
    $ mvn clean package -DskipTests

#### Geramos o `war` sem executar testes.

#### Após isso vá para a pasta `target` e verifique se o arquivo `war` do projeto foi gerado.

    api-crud-0.0.1-SNAPSHOT.war

## 4 Com o `war` gerado podemos criar o Dockerfile.

    FROM tomcat:latest

    EXPOSE 8080

    COPY ./loja.war /usr/local/tomcat/webapps

#### Mudei o nome para `loja.war` para facilitar o acesso pelo navegador.

## 5 Iremos na pasta onde está o Dockerfile e faremos o build da imagem com o comando:

    $ docker build -t tomcat-server-loja .

## 6. Agora podemos subir o container do Tomcat e verificar se está tudo funcionando normal.

    $ docker run -dit --name tomcat-server-loja -p 8080:8080 tomcat-server-loja:latest

## Como podemos ver o sistema da loja está UP.

![painel](/projeto-extra/Docker/imagens/inicio-sistema.png)

## As funcionalidades funcionando conforme o esperado.
- Cadastro de itens
![](/projeto-extra/Docker/imagens/teste-cadastro.png)
- Lista de itens
![](/projeto-extra/Docker/imagens/lista-estoque.png)

