# Full Stack Learning Project

## Description

Self-learning project designed to refresh and consolidate fundamental full stack developer skills. This repository implements a complete application using different technologies for both backend and frontend, with the goal of practicing key concepts and best practices in modern web development.

## Project Structure

The project is organized into four main modules:

### 📦 `/backend`
Backend developed with **Spring Framework** (Java).

**Features:**
- RESTful API following REST principles
- Dependency management with Maven/Gradle
- Layered architecture
- Data persistence with JPA/Hibernate
- Data validation and exception handling
- Security with Spring Security (optional)

### 🎨 `/frontend-angular`
Frontend application developed with **Angular**.

**Features:**
- Modular and reusable components
- Routing with Angular Router
- State management with services and RxJS
- HTTP communication with backend via HttpClient
- Reactive Forms for form handling
- TypeScript for static typing

### ⚛️ `/frontend-react`
Alternative frontend application developed with **React**.

**Features:**
- Functional components with Hooks
- State management (useState, useContext, Redux/Zustand)
- React Router for navigation
- REST API integration using fetch/axios
- Side effects handling with useEffect
- JSX/TSX for UI construction

### 🐳 `/docker`
Container configuration for project deployment.

**Contents:**
- `Dockerfile` for each service
- `docker-compose.yml` for service orchestration
- Network and volume configuration
- Environment variables for different environments

## Learning Objectives

- Implement a complete full stack architecture
- Compare two popular frontend frameworks (Angular vs React)
- Practice software design patterns and architecture
- Configure development environments with Docker
- Apply best practices for versioning and documentation

## Prerequisites

- **Java** 21 or higher
- **Node.js** 20 or higher
- **Docker** and **Docker Compose**
- **Maven**

## Installation and Execution

### With Docker (recommended)
```bash
# Clone the repository
git clone <repository-url>
cd <project-name>

# Start all services
docker-compose up -d
```

### Without Docker

#### Backend
```bash
cd backend
./mvnw spring-boot:run
```

#### Frontend Angular
```bash
cd frontend-angular
npm install
ng serve
```

#### Frontend React
```bash
cd frontend-react
npm install
npm start
```

## Technologies Used

### Backend
- Spring Boot
- Spring Data JPA
- Spring Security (if applicable)
- Database: PostgreSQL/MySQL/H2

### Frontend
- Angular (latest version)
- React (latest version)
- TypeScript
- CSS/SCSS or styling framework (Material UI, Tailwind, etc.)

### DevOps
- Docker
- Docker Compose

## Project Status

🚧 Under active development - Continuous learning project

## License

This project is created for educational purposes.

## Author

Wei He