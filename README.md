  # Book Management System
  
  Dự án quản lý sách sử dụng Spring Boot với tất cả các công nghệ Spring chính:
  - **Spring Boot** - Framework chính
  - **Spring MVC** - Web layer
  - **Spring Security** - Authentication & Authorization
  - **Spring Data JPA** - Data access layer
  - **Hibernate** - ORM framework
  - **MySQL** - Database
  - **Thymeleaf** - Template engine
  
  ## Tính năng
  
  - ✅ Đăng nhập/Đăng ký với Spring Security
  - ✅ Quản lý người dùng với roles (USER, OPERATOR, ADMIN)
  - ✅ CRUD operations cho sách
  - ✅ Tìm kiếm và lọc sách
  - ✅ Phân trang (pagination)
  - ✅ Validation với Bean Validation
  - ✅ MySQL database với Hibernate
  
  ## Yêu cầu hệ thống
  
  - Java 17+
  - Maven 3.6+
  - MySQL 8.0+
  - IDE (IntelliJ IDEA/VS Code)
  
  ## Cài đặt
  
  ### 1. Cài đặt MySQL
  
  #### Dùng MySQL Server trực tiếp
  # Windows: Tải từ https://dev.mysql.com/downloads/mysql/
  

  
  ### 2. Tạo Database
  
  ```sql
  -- Kết nối MySQL và chạy:
  CREATE DATABASE IF NOT EXISTS bookdb CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
  
  -- Tùy chọn: Tạo user riêng
  CREATE USER IF NOT EXISTS 'bookweb'@'localhost' IDENTIFIED BY 'bookweb';
  GRANT ALL PRIVILEGES ON bookdb.* TO 'bookweb'@'localhost';
  FLUSH PRIVILEGES;
  ```
  
  ### 3. Cấu hình Database
  
  Chỉnh sửa `application.properties`:
  ```properties
  # Với root user
  spring.datasource.username=root
  spring.datasource.password=root
  
  ```
  
  ### 4. Chạy ứng dụng
  
  ```bash
  # Clone project
  git clone <https://github.com/Ducnguyen-arch/BookStore.git>
  cd bookstore
  
  # Build và chạy
  mvn spring-boot:run
  
  # Hoặc
  mvn clean package
  java -jar target/BookStore-0.0.1-SNAPSHOT.jar
  ```
  
  ### 5. Truy cập ứng dụng
  
  - **Ứng dụng**: http://localhost:8080
  
  ## Tài khoản mặc định
  
  Hệ thống tự động tạo 3 tài khoản khi khởi chạy lần đầu:
  
  | Username | Password | Role | Mô tả |
  |----------|----------|------|-------|
  | admin | admin | ADMIN | Toàn quyền |
  | operator | operator | OPERATOR | Quản lý sách |
  | user | user | USER | Xem sách |
  

  ```
  ## API Endpoints
  
  ### Authentication
  - `GET /login` - Trang đăng nhập
  - `POST /login` - Xử lý đăng nhập
  - `GET /register` - Trang đăng ký
  - `POST /register` - Xử lý đăng ký
  - `POST /logout` - Đăng xuất
  
  ### Books
  - `GET /books` - Danh sách sách (có phân trang, tìm kiếm)
  - `GET /books/{id}` - Xem chi tiết sách
  - `GET /books/add` - Form thêm sách (OPERATOR+)
  - `POST /books/add` - Thêm sách mới (OPERATOR+)
  - `GET /books/edit/{id}` - Form sửa sách (OPERATOR+)
  - `POST /books/edit/{id}` - Cập nhật sách (OPERATOR+)
  - `POST /books/delete/{id}` - Xóa sách (ADMIN)
  
  ### Admin
  - `GET /admin/dashboard` - Trang quản trị (ADMIN)
  - `GET /admin/users` - Quản lý người dùng (ADMIN)
  - `GET /admin/users/{id}` - Chi tiết người dùng (ADMIN)
  - `POST /admin/users/{id}/status` - Kích hoạt/vô hiệu hóa user (ADMIN)
  
  ## Tech Stack
  
  ### Backend
  - **Spring Boot 3.5.4** - Framework chính
  - **Spring Security 6** - Bảo mật
  - **Spring Data JPA** - Data access
  - **Hibernate** - ORM
  - **MySQL Connector** - Database driver
  - **Bean Validation** - Validation
  - **Thymeleaf** - Template engine
  
  ### Frontend
  - **Bootstrap 5.1.3** - CSS Framework
  - **Font Awesome 6** - Icons
  - **Thymeleaf** - Server-side templating
  
  ### Database
  - **MySQL 8.0** - Relational database
  
  ### Thay đổi cấu hình Database
  Chỉnh sửa `application.properties` hoặc tạo profile riêng:
  ```properties
  spring.datasource.url=jdbc:mysql://your-host:3306/your-database
  spring.datasource.username=your-username
  spring.datasource.password=your-password
  ```
  
  ### Thêm role mới
  1. Thêm enum trong `User.Role`
  2. Cập nhật `SecurityConfig.java`
  3. Cập nhật UI templates
  
  ### Thêm entity mới
  1. Tạo entity class với JPA annotations
  2. Tạo repository interface
  3. Tạo service class
  4. Tạo controller
  5. Tạo Thymeleaf templates
  
  ## Troubleshooting
  
  ## Development
  
  ## Deploy
  
  ### WAR file
  Thay đổi packaging trong `pom.xml`:
  ```xml
  <packaging>war</packaging>
  ```
  
  ### Docker
  
  ## License
  
