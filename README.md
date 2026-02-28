# 🚀 Team Portfolio Website

A fully responsive, full-stack team portfolio website built with **HTML5**, **CSS3 (Flexbox + Grid)**, and **Java Spring Boot** backend with **MySQL** database.

---

## 📁 Project Structure

```
team port/
│
├── index.html                        ← Main frontend (all 7 sections)
├── style.css                         ← Complete responsive stylesheet
├── pom.xml                           ← Maven build config
│
├── assets/
│   └── images/                       ← Images directory
│
├── database/
│   └── schema.sql                    ← MySQL setup script
│
└── src/main/
    ├── java/com/teamportfolio/
    │   ├── TeamPortfolioApplication.java     ← Spring Boot entry point
    │   ├── model/
    │   │   └── ContactMessage.java           ← JPA Entity
    │   ├── repository/
    │   │   └── ContactRepository.java        ← Spring Data JPA
    │   ├── service/
    │   │   └── ContactService.java           ← Business logic
    │   ├── controller/
    │   │   └── ContactController.java        ← REST API endpoints
    │   └── config/
    │       └── CorsConfig.java               ← CORS setup
    └── resources/
        └── application.properties            ← DB + Server config
```

---

## 🌟 Features

### Frontend
| Section | Details |
|---------|---------|
| **Home / Hero** | Animated hero with typewriter effect, floating tech cards, animated stats counter |
| **About Us** | Two-column layout, Mission & Vision cards |
| **Team Members** | Responsive grid cards with initials, roles, bios, social links |
| **Skills** | Animated progress bars, tools grid |
| **Projects** | Filter tabs (All/Web/API/Mobile), project cards with overlay, GitHub + Live links |
| **Contact** | Real-time form validation, character counter, Spring Boot API submit |
| **Footer** | Social links, quick links, copyright with dynamic year |

### Backend (Spring Boot REST API)
| Endpoint | Method | Description |
|----------|--------|-------------|
| `/api/contact` | `POST` | Submit contact form (public) |
| `/api/contact` | `GET` | List all messages (admin) |
| `/api/contact/{id}` | `GET` | Get single message |
| `/api/contact/{id}/read` | `PATCH` | Mark as read |
| `/api/contact/{id}` | `DELETE` | Delete message |

---

## ⚙️ Setup & Run

### Prerequisites
- Java 17+
- Maven 3.8+
- MySQL 8.0+

### 1. Database Setup
```bash
mysql -u root -p < database/schema.sql
```

### 2. Configure Database
Edit `src/main/resources/application.properties`:
```properties
spring.datasource.url=jdbc:mysql://localhost:3306/team_portfolio_db?useSSL=false&serverTimezone=UTC
spring.datasource.username=root
spring.datasource.password=YOUR_PASSWORD
```

### 3. Build & Run Backend
```bash
mvn clean install
mvn spring-boot:run
```
Server starts at: **http://localhost:8080**

### 4. Open Frontend
Option A — Open `index.html` directly in a browser.

Option B — Place `index.html` and `style.css` in `src/main/resources/static/` and serve via Spring Boot at **http://localhost:8080**.

---

## 🧪 Test the API

```bash
# Submit a contact message
curl -X POST http://localhost:8080/api/contact \
  -H "Content-Type: application/json" \
  -d '{"name":"Test User","email":"test@example.com","subject":"Hello","message":"This is a test message."}'

# Get all messages
curl http://localhost:8080/api/contact
```

---

## 🎨 Design Highlights
- Dark theme with CSS custom properties (HSL color palette)
- Smooth scroll navigation with active link highlighting
- Intersection Observer for scroll-triggered animations
- CSS Grid + Flexbox responsive layouts
- Glassmorphism cards with backdrop-filter blur
- Mobile hamburger menu
- Typewriter effect, floating cards, animated orb backgrounds

---

## 📄 License
© 2024 Elite Dev Squad. All rights reserved.
