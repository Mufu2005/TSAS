# 🛠️ RFID-Based Employee Attendance System

Welcome to the **RFID-Based Attendance System**, a Java + Arduino integrated desktop application that offers a seamless way to record and manage employee check-ins using RFID technology.

With a sleek GUI and real-time communication via serial ports, this system is designed to bring automation and clarity to your workplace's attendance tracking.

---

## 🚀 Project Overview

📌 This project combines the power of **Java** and **Arduino** to automate employee attendance:

- Employees tap their **RFID tags** on an **RFID sensor** connected to an Arduino.
- The **Java application** detects these taps in real-time via serial port communication.
- Attendance is instantly recorded and saved to an **Excel file** for later reference.
- User credentials (Sign-Up/Login) are managed with **encrypted password storage** in a `.txt` file.

---

## 🎯 Key Features

- 🖥️ **Java-Based GUI** – Simple, intuitive, and user-friendly interface.
- 📲 **RFID Integration** – Quick and contactless attendance via RFID tags.
- 👥 **User Management** – Admins can **sign up**, **log in**, and **create employee profiles**.
- 📊 **Attendance Logging** – Automatically logs check-in times to an Excel spreadsheet.
- 🔐 **Encrypted Credentials** – Secure login with password encryption.
- ⚡ **Live Arduino Connection** – Real-time communication using selected **serial port**.

---

## 🗂️ Project Structure

```bash
RFID-Attendance-System/
├── Application/           # Java application with GUI and logic
│   ├── src/               # Source code files
│   ├── credentials.txt    # Encrypted user credentials
│   └── attendance.xlsx    # Excel sheet where attendance is stored
│
├── arduino code/          # Arduino sketch for RFID reader
│   └── rfid_attendance.ino
│
└── README.md              # This file
```
🛠️ Requirements
-Java Application
-Java JDK (8+)
-Excel API Library (Apache POI)
-Serial Communication Library (jSerialComm)

Arduino
-Arduino UNO (or compatible board)
-RFID Module (RC522 or similar)
-RFID Tags
-Arduino IDE

🔌 How to Use
Connect the Arduino
-Upload the code from the arduino code/ folder using the Arduino IDE.
-Make sure the RFID module is wired correctly.
Start the Java Application
-Run the Java program from the Application/ folder.
-Enter the serial number from the settings tab.
-🛑 This step is critical to establish the Arduino connection.
-Hard coded credentials: Admin, Admin.123 
Login or Sign Up
-New users can register via the Sign-Up screen.
-User credentials are stored securely with encrypted passwords.
-Add Employees
Create new employee profiles before scanning.
Record Attendance
-Employees tap their RFID tags.
-The system logs their check-in times in real-time.
Review Logs
-Attendance is saved to attendance.xlsx for future access.

🔒 Security Note
-Passwords are encrypted and saved in credentials.txt.
-Do not share this file unless absolutely necessary.

🌱 Future Improvements
-Add fingerprint or facial recognition.
-Enable logout and session tracking.
-Build an admin dashboard with charts and reports.
-Integrate with an online database or cloud storage.

👤 Author
Created with logic and love by Mufaddal Bhaijiwala, blending hardware and software into a single harmonious system.
