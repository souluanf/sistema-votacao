<div align="center">
<a href="https://www.sicredi.com.br" target="_blank">
    <img src="https://www.sicredi.com.br/static/home/assets/header/logo-svg2.svg" height="100px" alt="Mercado-Livre" class="center"/>
</a>

### Sistema de Votação</h3>

[![Java17](https://img.shields.io/badge/devel-Java17-brightgreen)](https://docs.oracle.com/en/java/javase/17)
[![SpringBoot](https://img.shields.io/badge/framework-SpringBoot-brightgreen)](https://docs.spring.io/spring-boot/docs/current/reference/htmlsingle)
[![Docker](https://img.shields.io/badge/container-Docker-brightgreen)](https://www.docker.com)
[![Maven](https://img.shields.io/badge/dependency--manager-Maven-brightgreen)](https://maven.apache.org/guides)
[![MYSQL](https://img.shields.io/badge/database-H2|Mysql-brightgreen)](https://www.h2database.com)
[![JUnit5](https://img.shields.io/badge/coverage-JUnit5-brightgreen)](https://junit.org/junit5/docs/current/user-guide)
[![MIT](https://img.shields.io/badge/license-MIT-brightgreen)](https://opensource.org/licenses/MIT)

</div>

## TL; DR

##### Documentação Swagger ->  [http://localhost:8080](http://localhost:8080)
##### Control Center ->  [http://localhost:9021](http://localhost:9021)
##### Repositório Github -> https://github.com/souluanf/sistema-votacao


## Framework e Bibliotecas
- **Java 17**: Develop
- **Spring Boot**: Framework;
- **Swagger(springfox, openapi)**: Para documentação.
- **Maven**: Para solução gerenciamento de dependencias.
- **Lombok**: Código clean.
- **Kafka (zookeeper,server,schema registry, control center)**: Mensageria.
- **Avro Schema**: Versionamento de schema.


### Execução

``` shell
docker-compose -f docker-compose.yml -p sistema-votacao up -d 
```

#### Abra dois terminais, cada um em uma pasta (votacao, consumer)

Na pasta `votacao`

```shell
mvn clean package -Dspring.profiles.active=test
mvn spring-boot:run
```

Na pasta `consumer`

```shell
mvn clean package
mvn spring-boot:run
```

1. Aponte o browser para  [http://localhost:8080](http://localhost:8080) para ver a documentação.
   - Aqui é possível ver a documentação e gerar requisições.
2. Aponte para  [http://localhost:9021](http://localhost:9021) para utilizar o control center.
   - Aqui é possível ver o tópico e enviar mensagens. A partir da primeira mensagem enviada já é possível ver o schema registrado no control center.



#### Observações

1. é preciso que as apps sejam subidas na ordem recomendada.
2. envio da mensagem para o kafka acontece quando há a chamada para o endpoint '/{id}/resultado'.
3. arquivo 'SessaoVotacao' é gerado pelo plugin do avro, através do arquivo 'src/main/resources/avro/sessao-votacao.avsc'


#### Melhorias a serem implementadas
1. Liquibase ou Flyway para versionamento de schema do banco;
2. Autenticação de usuários;
3. Aumento da cobertura de testes;
4. Utilização do webflux + mongo;
5. dockerfiles para build automático das app com docker-compose.;
6. CI/CD / Análise de qualidade, vulnerabilidade de código;
