Here is your content **properly structured in README.md (Markdown) format**, clean and professional, ready to paste directly into **README.md** in Android Studio or GitHub.

---

# Kids Math Quiz App

## Problem Statement

Children in early primary school often struggle to build confidence and speed in basic arithmetic operations such as **addition, subtraction, multiplication, and division**. Traditional learning methods can feel boring, leading to reduced interest and practice.

There is a need for an **engaging, interactive, and easy-to-use mobile application** that helps children improve their mathematical skills through **fun quizzes, instant feedback, and progressive difficulty levels**.

A **Quiz App** provides a playful environment where children can learn mathematics, practice questions, and track their performance in an enjoyable and motivating way.

---

##  Functional Requirements

### 1️⃣ User Interaction

* The app shall allow the user (child) to start a quiz.
* The app shall provide simple navigation buttons:

    * **Start**
    * **Next**
    * **Submit**
    * **Exit**

---

### 2️⃣ Quiz Generation

* The app shall generate arithmetic questions using the four operations:

    * Addition (+)
    * Subtraction (−)
    * Multiplication (×)
    * Division (÷)
* The app shall allow selection of difficulty levels:

    * Easy
    * Medium
    * Hard
* The app shall generate random numbers within predefined ranges.

---

### 3️⃣ Answer Input

* The app shall allow the user to enter answers through an input field.

---

### 4️⃣ Score Calculation

* The app shall check answers and award scores based on correct responses.
* The app shall display the final score at the end of the quiz.
* The app shall store or display:

    * Number of correct answers
    * Number of wrong answers

---

### 5️⃣ Feedback

* The app shall provide immediate feedback:

    * ✅ *Correct!* with a winning emoji 🎉
    * ❌ *Try again!* with a sad emoji 😢
* The app shall show the correct answer after **three incorrect attempts**.

---

### 6️⃣ Progress Tracking

* The app shall keep track of previous scores to support learning improvement.

---

## ⚙️ Non-Functional Requirements

These define how the system should behave.

### 1️⃣ Usability

* The app shall have a **kid-friendly interface** with:

    * Big buttons
    * Bright colors
    * Easy navigation
* The app shall use simple language understandable by young learners.

---

### 2️⃣ Performance

* The app shall load quiz questions instantly without delay.
* The app shall respond to user input within **1 second**.

---

### 3️⃣ Reliability

* The app shall run without crashing during quizzes.
* The app shall handle invalid input (e.g., empty answer fields).

---

### 4️⃣ Compatibility

* The app shall run smoothly on:

    * Android phones
    * Android tablets

---

### 5️⃣ Security

* The app shall not require sensitive personal information from children.
* The app shall store only necessary quiz scores locally.

---

### 6️⃣ Maintainability

* The app code shall be easy to update with:

    * New question types
    * Additional difficulty levels

---

##  Design

### ▶️ Play Activity

* The **Play Activity** handles quiz interaction, question display, answer input, feedback, and scoring.

---


