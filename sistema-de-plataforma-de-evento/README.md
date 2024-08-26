# SPDE - Sistema de Plataforma de eventos 🎟️

Sistema de Plataforma de eventos: Projeto Acadêmico Desenvolvido em **Java** com **Spring Boot**, **Spring Security**, **JWT**, **JDBCTemplate**, **PostgreSQL**, **Postman** e **Swagger** para a Disciplina de Desenvolvimento de Sistemas da *[Foursys](https://br.linkedin.com/company/foursys)*.

## Descrição 📋
O projeto ***Sistema para plataforde de eventos*** é um sistema de gerenciamento de eventos que permite aos usuários cadastrar, consultar, atualizar e excluir eventos. Além disso, o sistema permite o envio de email aos usuários sobre a compra, atualização e cancelamento do evento que escolheu participar, passando assim as informações do evento.

## Créditos


✍ Projeto proposto pela ***Fourcamp - *[Foursys](https://br.linkedin.com/company/foursys)***, disciplinado e orientado pelos mestres: *[Bruno Martin](https://www.linkedin.com/in/brunoermacora/)* e *[Denilson Elias](https://www.linkedin.com/in/denilsonbitit/)*

## Equipe de desenvolvimento 👨‍💻
- Scrum Master - *[Gustavo Rodrigues](https://www.linkedin.com/in/gustavo-rodrigues-443b641a4/)*
- Desenvolvedor - *[Luíz Felipe]()*
- Desenvolvedor - *[Ezaú Lira](https://www.linkedin.com/in/ezaulira/)*


## Tecnologias usadas 💻

- **JAVA**: linguagem de programação principal.


- **SPRING-BOOT**: Framework para desenvolvimento de aplicações Java.


- **POSTGRESQL**: Banco de dados relacional.


- **MAVEN**: Ferramenta principal utilizada para gerenciar essas aplicações em Java.


- **POSTMAN**: Ferramenta para testar e manipular os endpoints da API.


- **SWAGGER**: Documentação interativa dos endpoints.


- **INTELLIJ**: Ambiente de desenvolvimento.


<a href="https://www.oracle.com/java/technologies/downloads/#jdk22-windows"><img src="https://cdn.jsdelivr.net/gh/devicons/devicon/icons/java/java-original-wordmark.svg" width="50" height="50" /></a>
<a href="https://spring.io/projects/spring-boot"><img src="https://github.com/user-attachments/assets/2b843542-1437-44f2-b2f2-f33bae03b342" width="50" height="50" /></a>
<a href="https://www.postgresql.org/download/"><img src="https://github.com/user-attachments/assets/92f810dd-7297-4a86-b25f-3905fd976892" width="45" height="45" /></a>
<a href="https://maven.apache.org/download.cgi"><img src="https://github.com/user-attachments/assets/20c281e0-7784-46c2-b9c3-827979aa3391" width="45" height="45" /></a>
<a href="https://www.postman.com/downloads/"><img src="https://github.com/user-attachments/assets/3ead52df-8744-47a6-b932-e50ef1f86400" width="75" height="50" /></a>
<a href="https://swagger.io/tools/swagger-ui/"><img src="https://github.com/user-attachments/assets/d1358131-cdcc-4812-a72d-27426ef48bc6" width="120" height="50" /></a>
<a href="https://www.jetbrains.com/idea/download/"><img src="https://github.com/user-attachments/assets/7949db83-7fcb-4611-ba0e-ec37a4af28e3" width="40" height="40" /></a>



## Configurações de Banco de Dados 🗄️
O sistema está configurado para usar um banco de dados PostgreSQL. As configurações de conexão estão no arquivo `application.yaml`:

```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/todos_eventos_db
spring.datasource.username=seu_usuario
spring.datasource.password=sua_senha
spring.jpa.hibernate.ddl-auto=update
spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.PostgreSQLDialect
````
## Configuração do Swagger ⚙️
O Swagger está configurado para documentar e testar as APIs do sistema. A documentação do Swagger pode ser acessada em: http://localhost:27031/swagger-ui.html.

## Como Executar o Projeto 🛠
 1. Clone o repositório:
    - git clone *[repositório](https://github.com/GustavoRSSBr/plataforma-eventos.git)*


 2. Configure o banco de dados PostgreSQL e atualize as credenciais no ***application.properties.***


 3. Execute o projeto usando Maven:
    - localizado na EventosApplication.
    <div align="">
    <img src="https://github.com/user-attachments/assets/8103c639-4f80-4d46-ad91-baa2d132ab32" height="400" />
    </div>

## Scrip da base de dados e as functions ⚙️
 
- Link: https://bitbucket.org/spe-todos-ventos/script_e_fuctions_basededados/src/master/

- Observção: Realizar a execução do script da base de dados primeiro, e logo apos realizar a execução dos scripts das functions
