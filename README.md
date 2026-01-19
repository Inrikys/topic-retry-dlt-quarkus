# 📦 API de Avaliação de Produtos

## 📌 Descrição
Esta API permite que usuários avaliem produtos com notas, comentários e imagens.  
O sistema aplica regras de negócio para garantir a integridade das avaliações e utiliza processamento assíncrono para recalcular a média dos produtos.

A aplicação foi pensada para exercitar:
- CRUD completo
- Regras de negócio
- Persistência em banco de dados
- Armazenamento de arquivos no S3
- Mensageria com Kafka (retry + DLT)
- Programação reativa no Quarkus

---

## 🧱 Arquitetura (Visão Geral)

- **API REST (Quarkus)**
- **Banco de Dados Relacional** (PostgreSQL, MySQL, etc.)
- **S3** para armazenamento de imagens
- **Kafka** para processamento assíncrono de eventos

---

## 🗄️ Modelo de Dados (Banco)

### Product
- `id`
- `name`
- `description`
- `avgReviews`
- `totalReviews`

### Review
- `id`
- `productId`
- `userId`
- `score` (1 a 5)
- `commentery`
- `status` (`APROVED`, `SUSPECT`)
- `creationDate`

### User
- `id`
- `name`
- `email`

---

## ☁️ Armazenamento S3

- Imagens originais da avaliação
- Miniaturas (thumbnails) geradas a partir das imagens

---

## 📜 Regras de Negócio

- Um usuário **só pode avaliar um produto uma única vez**
- A nota da avaliação deve estar entre **1 e 5**
- Comentários com palavras ofensivas são marcados como `SUSPEITA`
- Avaliações `SUSPEITA` **não entram no cálculo da média**
- A média do produto é recalculada **de forma assíncrona**
- Avaliações não podem ser alteradas após aprovação

---

## 🔄 Fluxo Principal

1. Usuário cria uma avaliação via API REST
2. Avaliação é salva no banco de dados
3. Imagens são enviadas para o S3
4. Evento `avaliacao-criada` é publicado no Kafka
5. Consumer processa o evento e recalcula a média do produto

---

## 📨 Kafka

### Tópicos
- `avaliacao-criada`
- `avaliacao-criada-retry`
- `avaliacao-criada-dlt`

### Producer
- Publica evento sempre que uma nova avaliação é criada

### Consumer
Responsável por:
- Recalcular a média de avaliações do produto
- Atualizar os campos `mediaAvaliacoes` e `totalAvaliacoes`

---

## 🔁 Retry e DLT

### Retry
- Até **3 tentativas**
- Backoff exponencial
- Usado para falhas temporárias (ex: concorrência no banco)

### DLT (Dead Letter Topic)
Mensagens são enviadas para o DLT quando:
- Produto não existe
- Dados inconsistentes
- Falhas permanentes de processamento

---

# simple-api

This project uses Quarkus, the Supersonic Subatomic Java Framework.

If you want to learn more about Quarkus, please visit its website: <https://quarkus.io/>.

## Running the application in dev mode

You can run your application in dev mode that enables live coding using:

```shell script
./mvnw quarkus:dev
```

> **_NOTE:_**  Quarkus now ships with a Dev UI, which is available in dev mode only at <http://localhost:8080/q/dev/>.

## Packaging and running the application

The application can be packaged using:

```shell script
./mvnw package
```

It produces the `quarkus-run.jar` file in the `target/quarkus-app/` directory.
Be aware that it’s not an _über-jar_ as the dependencies are copied into the `target/quarkus-app/lib/` directory.

The application is now runnable using `java -jar target/quarkus-app/quarkus-run.jar`.

If you want to build an _über-jar_, execute the following command:

```shell script
./mvnw package -Dquarkus.package.jar.type=uber-jar
```

The application, packaged as an _über-jar_, is now runnable using `java -jar target/*-runner.jar`.

## Creating a native executable

You can create a native executable using:

```shell script
./mvnw package -Dnative
```

Or, if you don't have GraalVM installed, you can run the native executable build in a container using:

```shell script
./mvnw package -Dnative -Dquarkus.native.container-build=true
```

You can then execute your native executable with: `./target/simple-api-1.0.0-SNAPSHOT-runner`

If you want to learn more about building native executables, please consult <https://quarkus.io/guides/maven-tooling>.

## Provided Code

### REST

Easily start your REST Web Services

[Related guide section...](https://quarkus.io/guides/getting-started-reactive#reactive-jax-rs-resources)
