# Blog Website Backend

This is the backend of a blog website built using **Spring Boot**. The project focuses on clean architecture, secure authentication, and efficient data management. It includes features for managing posts, categories, and comments, along with image storage using AWS S3.

---

## 🚀 Features

- **Spring Security with JWT**:  
  Implements secure authentication and authorization.
  
- **Post Management**:  
  Create, update, and delete blog posts.

- **Category Management**:  
  Organize posts into meaningful categories.

- **Commenting System**:  
  Users can interact with posts by adding comments.

- **Image Storage**:  
  Integrated with **AWS S3** for storing user and post images.

- **Clean Architecture**:  
  The project is structured with dedicated packages for better maintainability:
  - `controllers`  
  - `config`  
  - `exceptions`  
  - `services`  
  - `repositories`  
  - `entities`

---

## 🛠️ Tech Stack

- **Java**  
- **Spring Boot**  
- **Spring Security**  
- **PostgreSQL**  
- **AWS S3**  

---

## 🗄️ Database Design

### Tables:
- **Post**: Stores blog post details.  
- **Category**: Organizes posts into categories.  
- **Comment**: Stores comments on posts.  
- **User**: Manages user data for authentication and profile.

---

## 🌐 API Endpoints

### Authentication

### Posts

### Categories

### Comments

---

## 🖼️ Image Storage with AWS S3

- **AWS S3** is used to store images for posts and users.  
- Images are uploaded to S3 buckets and accessed via their URLs.

---

### Prerequisites
- **Java 17+**  
- **PostgreSQL**  
- **AWS S3 Account**



