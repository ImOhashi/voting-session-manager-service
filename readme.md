# Voting session manager service

### Descritivo da aplicação:

- Versão do Java: 21
- Versão do Spring: 3.4.0
- Base de dados: Mongo DB
- Menssageria: Kafka

### Como iniciar a aplicação:

Na raiz do projeto existe um arquivo chamado <i>variables.local.env</i>, neste arquivo contém todas as 
variáveis necessárias para rodar a aplicação. Seriam elas:

- SERVER_PORT
- MONGO_DB_URL
- SESSION_TIME_SECONDS
- KAFKA_BROKER_URL
- RESULT_TOPIC_NAME

Estas variáveis são configuráveis de acordo com as necessidades do projeto e do ambiente do desenvolvedor,
e as mesmas contém um valor default no arquivo application.yml. Para configurar as variáveis recomenda-se que
injete os valores na configuração de <i><b>Enviroment Variables</i></b> de sua IDE de preferência. No caso de
estar utilizando a IDE <i><b>IntelliJ</b></i>, recomendo o uso do plugin <i><b>EnvFile</b></i> para auxiliar no uso de
seu arquivo de configuração.

### Infraestrutura

Toda a infraestrutura do projeto encontra-se configurada via <i><b>Docker Compose</b></i> tanto para padronizar como para
facilitar a configuração do ambiente de desenvolvimento, para iniciar basta utilizar o seguinte comando na raiz do projeto 
após a instalação do
<i><b>Docker</b></i>:

```
docker compose up -d
```

O mesmo inicializará a base de dados e toda estrutura necessária para o <i><b>Kafka Broker</b></i>. E falando do mesmo,
fica como responsábilidade da classe de configuração da aplicação criar o tópico Kafka que será utilizado pela aplicação.