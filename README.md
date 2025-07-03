# Doctor-Patient Appointment System

A 3-tier Java web application designed for managing doctor-patient appointments.

## 🛠 Technologies Used

- **Java Servlets** – Backend business logic
- **JSP & HTML/CSS/JavaScript** – Frontend UI and interaction
- **Apache Tomcat 8.5** – Application server
- **MySQL** – Relational database management
- **Session Management** – For secure login/logout
- **Password Security** – Hashing with salt for stored credentials

## ✅ Features

### 🧑‍⚕️ Doctor
- Set availability for appointments
- View scheduled appointments (weekly/monthly)
- Cancel appointments

### 👤 Patient
- Register and log in
- Search and book available appointments by specialty, name, or AMKA
- View appointment history
- Cancel upcoming appointments

### 🛠 Administrator
- Add/delete doctors, patients, and other admins
- Prevent duplicate AMKA or usernames
- Session and data consistency enforcement

## 🗃 Database
- MySQL schema with secure hashed passwords and session attributes
- Referential integrity using foreign keys
- Constraints to avoid overlapping appointments

> Developed as part of the course “Global Web Programming” at University of Piraeus.
