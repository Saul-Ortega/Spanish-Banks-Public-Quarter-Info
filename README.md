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
│   ├── docker-compose.yml
│   ├── Dockerfile
│   ├── mvnw
│   ├── mvnw.cmd
│   ├── pom.xml
│   ├── src
│   └── target
├── LICENSE
├── readme-assets/
├── README.md
└── scraper
    ├── main.ts
    ├── node_modules/
    ├── package.json
    ├── pnpm-lock.yaml
    ├── pnpm-workspace.yaml
    ├── services/
    └── types/

```

### 📁 Api Structure Example

```
bank/
├── BankController.java     # REST Endpoints
├── Bank.java               # Entity
├── BankRepository.java     # DB Access
└── BankService.java        # Business Logic
```

### 📁 Scraper Structure

```
.
├── main.ts                 # Entry point
├── services/               # API operations
└── types/                  # Interfaces
```

## 🔗 Relational Model

<div align="center">
    <img src="./readme-assets/Relational-Model-API.webp" alt="Relational Model of the API" />
</div>

## ▶️ Installation

1. Navigate to the api folder
    ```bash
      cd api
    ```

2. Run the container with the api and the database
    ```bash
      docker compose up
    ```

3. Open a new terminal and navigate to the scraper folder
    ```bash
      cd ../scraper
    ```

4. Install the dependencies
    ```bash
      pnpm install
    ```

5. Run the scraper
    ```bash
      pnpm start
    ```

## 📋 OpenAPI / Swagger Documentation

Once the container is running, you can navigate to the API documentation.

```bash
  http://localhost:8080/swagger-ui/index.html
```

<div align="center">
    <img src="./readme-assets/Swagger-API.png" alt="OpenAPI / Swagger screenshot" />
</div>

## ⚙️ Overview

### API Demo

![API Demo](./readme-assets/Demo-API.gif)

### Scraper Demo

![Scraper Demo](./readme-assets/Demo-SCRAPER.gif)

## 🎯 Future Improvements

- Add Json Web Token.

- Improve validations api.

- Persist scraper logs in a file.

- Implement CI/CD with Github Actions