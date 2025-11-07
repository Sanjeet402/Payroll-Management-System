# Payroll Management System (Spring Boot + React)

A full-stack payroll management system with a Spring Boot (Java) backend and a React frontend. Features include secure authentication (JWT), employee and payroll management, and API documentation via Swagger/OpenAPI.

## Tech Stack

- **Backend**: Spring Boot 3, Spring Web, Spring Data JPA, Spring Security, JWT, PostgreSQL, Maven
- **Frontend**: React (CRA), React Router, Axios, Bootstrap
- **Docs**: springdoc-openapi (Swagger UI)


## Prerequisites

- **Java**: 17+
- **Maven**: 3.9+
- **Node.js**: 18+ (and npm)
- **PostgreSQL**: 14+ (or compatible)

## Backend Setup (Spring Boot)

- Dependencies:
  - Spring Web, Spring Data JPA, Spring Security
  - PostgreSQL driver
  - JWT: `io.jsonwebtoken:jjwt-*`
  - Swagger UI: `org.springdoc:springdoc-openapi-starter-webmvc-ui`


- **Run**
mvn spring-boot:run

  Backend runs at `http://localhost:8080`.

- **API Docs**
  - Swagger UI: `http://localhost:8080/swagger-ui/index.html`
  - OpenAPI JSON: `http://localhost:8080/v3/api-docs`

## Frontend Setup (React)

- **Environment**
  Create `.env` in the frontend root:
REACT_APP_API_BASE_URL=http://localhost:8080

  Use this in code via `process.env.REACT_APP_API_BASE_URL`.

- **Install & Run**
  npm install npm start

  Frontend runs at `http://localhost:3000`.


## Build

- **Backend**
mvn clean package

  Produces a runnable JAR in `target/`.

- **Frontend**
npm run build

  Static assets output in `build/`.

## Common Scripts

- **Backend**
  - `mvn spring-boot:run` — start dev server
  - `mvn test` — run tests (if present)

- **Frontend**
  - `npm start` — start dev server
  - `npm run build` — production build
  - `npm test` — run tests

## Folder-by-Folder Notes

- **Payroll-backend/.../pom.xml**
  - Spring Boot 3.1.4
  - Java 17
  - Swagger/OpenAPI preconfigured

- **final-frontend/final-frontend**
  - React, React Router, Axios, Bootstrap
  - CRA scripts


## Troubleshooting

- Ensure your PostgreSQL DB is reachable and credentials match `application.properties`.
- If CORS issues occur, update allowed origins in backend config to match the frontend URL.
- If React fails to start, ensure Node 18+ and delete `node_modules` then reinstall.

## License

Add your preferred license (e.g., MIT).
.gitignore (recommended)
Place this at the repository root to cover Java, Node, and IDE artifacts:

gitignore
# Java / Maven
target/
!.mvn/wrapper/maven-wrapper.jar
.mvn/
*.log
*.iml

# Node
node_modules/
npm-debug.log*
yarn-debug.log*
yarn-error.log*
pnpm-debug.log*
dist/
build/
