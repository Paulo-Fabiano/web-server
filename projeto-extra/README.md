# Projeto Extra

#### Como projeto extra, escolhi utilizar um sistema que desenvolvi com Java e Thymeleaf para a loja da minha mãe. Então irei configurar o sistema para utilizar o servidor de aplicação JBoss, que será configurado com o Docker. 

#### Como já temos instalado em nossa máquina o Docker, que instalamos anteriormente via script inicial, nós iremos seguir alguns passos para configurar tudo.

- Fazer o clone do projeto.
- Criar um Dockerfile do Jboss.
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

## 3 Usamos um comando para gerar um arquivo `war`.
    $ mvn package

## 4 Com o `war` gerado podemos criar o Dockerfile.

