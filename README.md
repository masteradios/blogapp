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

## 📦 Dependencies

The project uses the following dependencies for its core functionality:

```xml

		<dependency>
			<groupId>org.modelmapper</groupId>
			<artifactId>modelmapper</artifactId>
			<version>3.1.1</version>
		</dependency>

		<dependency>
			<groupId>org.springframework.boot</groupId>
			<artifactId>spring-boot-starter-validation</artifactId>
		</dependency>

		<dependency>
			<groupId>org.springframework.boot</groupId>
			<artifactId>spring-boot-starter-data-jpa</artifactId>
		</dependency>
		<dependency>
			<groupId>org.springframework.boot</groupId>
			<artifactId>spring-boot-starter-web</artifactId>
		</dependency>

		<dependency>
			<groupId>org.postgresql</groupId>
			<artifactId>postgresql</artifactId>
			<scope>runtime</scope>
		</dependency>
		<dependency>
			<groupId>org.projectlombok</groupId>
			<artifactId>lombok</artifactId>
			<version>1.18.36</version>
			<scope>provided</scope>
		</dependency>
		<dependency>
			<groupId>org.springframework.boot</groupId>
			<artifactId>spring-boot-starter-test</artifactId>
			<scope>test</scope>
		</dependency>

		<!-- https://mvnrepository.com/artifact/com.amazonaws/aws-java-sdk -->
		<dependency>
			<groupId>com.amazonaws</groupId>
			<artifactId>aws-java-sdk</artifactId>
			<version>1.12.780</version>
		</dependency>

		<dependency>
			<groupId>org.springframework.boot</groupId>
			<artifactId>spring-boot-starter-security</artifactId>
		</dependency>

		<dependency>
			<groupId>io.jsonwebtoken</groupId>
			<artifactId>jjwt-api</artifactId>
			<version>0.12.6</version>
		</dependency>
		<dependency>
			<groupId>io.jsonwebtoken</groupId>
			<artifactId>jjwt-impl</artifactId>
			<version>0.12.6</version>
			<scope>runtime</scope>
		</dependency>
		<dependency>
			<groupId>io.jsonwebtoken</groupId>
			<artifactId>jjwt-jackson</artifactId> <!-- or jjwt-gson if Gson is preferred -->
			<version>0.12.6</version>
			<scope>runtime</scope>
		</dependency>

---

### Prerequisites
- **Java 17+**  
- **PostgreSQL**  
- **AWS S3 Account**



