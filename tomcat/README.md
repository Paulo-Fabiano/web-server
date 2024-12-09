# Documentação: Configurando Jenkins e Tomcat com Docker

#### Esta documentação orienta a configuração de um servidor Tomcat para hospedar o Jenkins para servir como servidor de automação utilizando Docker

#### Orientações

- Estou utilizando uma máquina com Ubuntu 24

## 1. Configuração dos containers

#### Primeiro iremos configurar o Docker na máquina.

### 1.1 Verificar se o `curl` está instalado na máquina, caso não esteja devemos instalar com o seguinte comando

    $ sudo apt install curl -y 

### 1.2 Instalar o Docker na máquina usando o scritp oficial 

    $ curl -fsSL https://get.docker.com | bash

### 1.3 Este passo é opcional, mas deve ser feito para seguir boas práticas e não ficar usando o sudo para executar o Docker somente com o `sudo`. Iremos adicionar o grupo do docker ao usuário que estamos usando.

    $ sudo usermod -aG docker paulo
 
## 2. Após o Docker estar configurado e funcionando em nossa máquina, iremos fazer o download do arquivo WAR no site oficial do Jenkins, criar um Dockerfile, buildar a imagem e executar.

### 2.1 Fazendo o download no site oficial do Jenkins

- [Site oficial](https://www.jenkins.io/download/)
- Lembre-se de fazer o download do arquivo `.war`

### 2.2 Iremos criar o Dockerfile usando como base a imagem do Tomcat

    FROM tomcat:latest

    EXPOSE 8080

    COPY ./jenkins.war /usr/local/tomcat/webapps

- Estamos expondo a porta 8080 do container e copiando o arquivo `.war` que baixamos anteriormente e movemos para a pasta que está o Dockerfile.

### 2.3 Agora iremos buildar o Dockerfile e gerar uma imagem docker que irá servir como base para executar o Tomcat

    $ docker build -t tomcat .

### 2.4 Com a imagem gerada iremos executar o container e acessar para verificar se já é possível acessar o Jenkins pelo navegador.

    $ docker run -dit -p 8080:8080 --name tomcat tomcat:latest

![](/tomcat/imagens/jenkins.png)

#### Podemos ver que o Jenkins está funcionado ao acessá-lo pelo navegador web com a url `http://localhost:8080/jenkins`

## 3. Agora iremos configurar o Jenkins

### 3.1 Primeiro iremos ver a senha inicial usando o comando abaixo.

    $ docker exec -it tomcat cat /root/.jenkins/secrets/initialAdminPassword

- Com a senha inicial, é só copiar e seguir para os próximos passos.

### 3.2 Configurando um usuário inicial.
![usuario-inicial](/tomcat/imagens/criar-usuario-jenkins.png)

### 3.3 Instalando as extensões sugeridas.
![instalando-extensoes](/tomcat/imagens/jenkins-configuracoes.png)

### 3.4 Após a configuração inicial iremos instalar o plugin do Prometheus para monitorar o Jenkins
![extensão-prometheus](/tomcat/imagens/extensao-prometheus.png)

## 4. Agora iremos criar um arquivo `docker-compose.yml` e criar os serviços.

    services:

    grafana:
        container_name: grafana 
        image: grafana/grafana:latest
        ports:
        - "3000:3000"

    prometheus:
        container_name: prometheus
        image: prom/prometheus:latest
        ports:
        - "9090:9090"
        volumes:
        - "/home/paulo/Documentos/web-server/tomcat/Docker/prometheus/prometheus.yml:/etc/prometheus/prometheus.yml"
    
- Usando as imagens que foram sugeridas.
- Irei montar um volume do container do Prometheus na pasta `/prometheus/prometheus.yml` para quaisquer atualizações posteriores ficarem mais fáceis. Mais a frente irei explicar melhor o que é esse arquivo.

## 5. Configurando o Prometheus

#### Para configurar o Prometheus, podemos escrever um arquivo com a extensão `yml` que conterá as intruções de onde o Prometheus irá coletar as métricas e outras configurações relacionadas a seu funcionamento. Ao montar o arquivo como um volume, qualquer alteração que fizermos no arquivo será automaticamente refletida no container, sem precisar reconstruir ou reiniciar o container, facilitando a atualização da configuração.

    scrape_configs:
    - job_name: 'jenkins'
        metrics_path: /prometheus
        static_configs:
        - targets: ["jenkins:8080"]  # Ajuste a porta, se necessário

- scrape_configs defie os jobs, ou seja, tarefas, que o Prometheus executa.
- job_name é o nome da tarefa.
- metrics_path é o caminho onde o Jenkins expõe as métricas. 
- static_config defini o alvo que iremos monitorar


## 6. Configurando o Grafana

#### Após o container do Grafana estar UP, iremos acessar a url `http://localhost:3000` para configurar o Grafana

![grafana](/tomcat/imagens/grafana.png)

#### As credenciais padrão do grafana são `Username = admin / Password = admin`

#### Após acessar o Grafana iremos configurar o datasource, que no nosso caso vai ser o Prometheus.

### 6.1 Clique na opção `Data Sources` no menu lateral.
![data-source](/tomcat/imagens/data-source.png)
1. Iremos fazer o download do arquivo WAR do Jenkins no site oficial

### 6.2 Após isso clique na opção ` Add data source `
![add-data-source](/tomcat/imagens/add-data-source.png)

### 6.3 Selecione o Prometheus como Data Source
![prometheus](/tomcat/imagens/prom-data.png)

### 6.4 Nessa etapa inserimos as configurações e testamos

- Connection = http://localhost:9090

#### Após isso é só clicar em `Save & test`

## Com tudo configurado, podemos montar nosso primeiro dashboard no Grafana. Iremos montar o dashboard buscando as informações sobre se o Jenkins está UP
![dashboard](/tomcat/imagens/primeiro-dashboard.png)
## Agora já podemos monitorar se o nosso Jenkins está UP.