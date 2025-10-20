# 🌐 Kaiburr Task 3 — Web UI for Task Management

## 📘 Overview
This project implements a **React + TypeScript frontend** for the backend REST API developed in **Kaiburr Task 1**.  
It allows users to create, view, search, delete, and execute shell command tasks through a simple and responsive web interface.

This submission corresponds to **Task 3** of the **Kaiburr Assessment 2025**.

---

## ⚙️ Features

✅ Create a new Task (with name, owner, and command)  
✅ View all existing Tasks  
✅ Search Tasks by name  
✅ Delete Tasks  
✅ Execute commands and view output in real time  
✅ REST API connection to the backend built in Task 1  
✅ User-friendly UI built with **Ant Design** and **React 19**  
✅ Fully responsive layout  

---

## 🧱 Technology Stack

| Component | Technology |
|------------|-------------|
| Frontend Framework | React 19 (with Vite or CRA) |
| Language | TypeScript |
| UI Library | Ant Design |
| HTTP Client | Axios / Fetch |
| Styling | Tailwind CSS |
| Backend API | Spring Boot (Task 1) |
| Database | MongoDB |

---

kaiburr_task3/
├── src/
│ ├── components/
│ │ ├── TaskForm.tsx # Form to create a task
│ │ ├── TaskList.tsx # Table of all tasks
│ │ └── TaskOutputModal.tsx # Popup to show execution results
│ ├── pages/
│ │ └── Home.tsx # Main UI page
│ ├── App.tsx
│ ├── main.tsx
│ ├── index.css
│ └── services/
│ └── api.ts # Axios instance to call backend APIs
├── package.json
├── tsconfig.json
└── README.md


---

## 🧰 Installation & Setup

### 1️⃣ Clone the Repository
```bash
git clone https://github.com/honeygyani/Kaiburr_Task3.git
cd Kaiburr_Task3

🧩 How It Works

The user fills out the Task Form and submits it.

The app sends a PUT request to the backend /tasks endpoint.

All tasks are displayed in a table with options to execute or delete.

When the user clicks Run, a PUT request to /tasks/{id}/execute is triggered and the output is displayed in a modal popup.

## 📂 Folder Structure

