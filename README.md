# Inventory Management

A full-stack web application for managing inventory, products, and stock movements. This project consists of a Spring Boot backend API and a Vue.js frontend application.

## Technologies

### Backend
- Java 17
- Spring Boot 3.5.0
- Spring Data JPA
- H2 Database
- Maven

### Frontend
- Vue.js 3
- TypeScript
- Vite
- Vue Router
- Axios
- SweetAlert2
- FontAwesome

## Prerequisites

- Java 17.0.12
- Node.js v22.16.0
- npm
- Maven

### Backend Setup and Running

1. Navigate to the backend directory:
```bash
cd backend
```

2. Install dependencies and build the project:
```bash
mvn clean install
```

3. Run the project:
```bash
java -jar target/inventory-management-0.0.1-SNAPSHOT.jar
```

The backend server will start on `http://localhost:8080`

### Frontend Setup and Running

1. Navigate to the frontend directory:
```bash
cd frontend
```

2. Install dependencies:
```bash
npm install
```

3. Run the project:
```bash
npm run dev
```

The frontend application will be available at `http://localhost:4173`

## Testing

### Backend Tests
```bash
cd backend
mvn test
```

## Development

- Backend API documentation is available in the [Postman collection](Inventory%20Management%20API.postman_collection.json)
- Frontend development server includes hot-reload for quick development
- ESLint and Prettier are configured for code formatting and linting

## License

This project is licensed under the MIT License.