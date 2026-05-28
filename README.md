# 🏦 Spanish Banks Public Quarter Info

Spanish banks are legally required to publish their quarterly financial information to the Bank of Spain. This project **automates** the extraction of that data via a **web scraper** that navigates to the Bank of Spain's public portal, and stores the results in a PostgreSQL database through a **Spring Boot REST API**.

## 🔄 Workflow

<div align="center">
    <img src="./readme-assets/Workflow.png" alt="Workflow screenshot" />
</div>

## 🛠️ Stack

[![Java](https://img.shields.io/badge/Java-%23ED8B00.svg?logo=openjdk&logoColor=white)](#)
[![Spring Boot](https://img.shields.io/badge/Spring%20Boot-6DB33F?logo=springboot&logoColor=fff)](#)
[![Hibernate](https://img.shields.io/badge/Hibernate-59666C?logo=hibernate&logoColor=fff)](#)
[![JUnit5](https://img.shields.io/badge/JUnit5-C21325?logo=junit5&logoColor=fff)](#)
[![Mockito](https://img.shields.io/badge/Mockito-C21325?logo=Mockito&logoColor=fff)](#)
[![Postgres](https://img.shields.io/badge/Postgres-%23316192.svg?logo=postgresql&logoColor=white)](#)
[![TypeScript](https://img.shields.io/badge/TypeScript-3178C6?logo=typescript&logoColor=fff)](#)
![Puppeteer](https://img.shields.io/badge/Puppeteer-%2340B5A4.svg?logo=Puppeteer&logoColor=black)
[![OpenAPI](https://img.shields.io/badge/OpenAPI-6BA539?logo=openapiinitiative&logoColor=white)](#)
[![Swagger](https://img.shields.io/badge/Swagger-85EA2D?logo=swagger&logoColor=173647)](#)
[![Docker](https://img.shields.io/badge/Docker-2496ED?logo=docker&logoColor=fff)](#)

## 🏗️ Project Structure
```
.
├── api/
│   ├── docker-compose.yml      # PostgreSQL and Spring Boot services
│   ├── Dockerfile
│   ├── mvnw
│   ├── mvnw.cmd
│   ├── pom.xml
│   └── src/
├── LICENSE
├── readme-assets/              # Assets used by the README
├── README.md
└── scraper/
    ├── main.ts                 # Entry point
    ├── package.json
    ├── pnpm-lock.yaml
    ├── pnpm-workspace.yaml
    ├── services/               # API operations
    └── types/                  # Interfaces

```

### 📁 Api Structure Example

```
bank/
├── BankController.java     # REST Endpoints
├── Bank.java               # Entity
├── BankRepository.java     # DB Access
└── BankService.java        # Business Logic
```

## 🔗 Relational Model

<div align="center">
    <img src="./readme-assets/Relational-Model-API.webp" alt="Relational Model of the API" />
</div>

## ▶️ API Installation

1. Navigate to the api folder
    ```bash
      cd api
    ```

2. Run the container with the api and the database
    ```bash
      docker compose up
    ```

### API Demo

![API Demo](./readme-assets/Demo-API.gif)

## ▶️ Scraper Installation

1. Open a new terminal and navigate to the scraper folder
    ```bash
      cd scraper
    ```

2. Install the dependencies
    ```bash
      pnpm install
    ```

3. Run the scraper
    ```bash
      pnpm start
    ```

### Scraper Demo

![Scraper Demo](./readme-assets/Demo-SCRAPER.gif)

## 📋 OpenAPI / Swagger Documentation

Once the container is running, you can navigate to the API documentation.

```bash
  http://localhost:8080/swagger-ui/index.html
```

<div align="center">
    <img src="./readme-assets/Swagger-API.png" alt="OpenAPI / Swagger screenshot" />
</div>

## 🎯 Future Improvements

- Add JSON Web Token.
- Improve validations to API endpoints.
- Persist scraper logs in a file.
- Implement CI/CD with Github Actions.

## ⚖️ License

This project is licensed under the [MIT License](LICENSE).