📅 EventSnap – Simple Calendar Utility App

EventSnap is a simple and clean Android application built using Kotlin and Firebase, designed to help users view, add, and manage events efficiently.
This project was developed as part of the Android & Backend Developer Assignment for HorizonX Playr.

🚀 Features Overview
🏠 Home Screen

Displays a list of events using RecyclerView

Events are fetched from:

A public REST API (hosted on GitHub)

Each event item shows:

Event Title

Event Date

Short Description

➕ Add Event

Users can add new events with:

Event Title

Event Date (Calendar Picker)

Optional Notes

Events are saved to Firebase Firestore

Input validation included

Success / failure feedback shown

📄 Event Detail Screen

Displays full event details

Data loaded from Firebase Firestore or API

🔥 Firebase & Backend Integration

Firebase Authentication

Email-based authentication

Separate and seamless Signup & Login flow

Firebase Firestore

Stores all user-created events

Firebase Realtime Database

Firebase Analytics

Logged key events:

App Open

Event Added

Event Viewed

Firebase Cloud Messaging (FCM)

Live notifications implemented

Firebase Remote Config

Feature toggle functionality (e.g., enable/disable UI features)

Firebase Crashlytics

Enabled for crash monitoring

🌐 Public API Used

Events are fetched from a public JSON hosted on GitHub:

https://raw.githubusercontent.com/nikka471/SimpleCalendarUtility_App/refs/heads/main/events.json


API consumption is handled using Retrofit.

🏗 Architecture & Tech Stack

Language: Kotlin

Architecture: MVVM (Basic implementation)

UI: Material Design Components

Networking: Retrofit

Local Storage: SharedPreferences

Backend: Firebase

Authentication

Firestore

Realtime Database

Analytics

FCM

Remote Config

🔐 Additional Functionalities

Logout functionality

Persistent login using SharedPreferences

Calendar-based date selection

Clean and user-friendly UI

📹 Demo & Submission Details

Screen recordings and screenshots are included (shared via email)

APK: Available on request (can be shared via WhatsApp due to email size limits)

📌 Notes

This project focuses on functionality, clarity, and core Android fundamentals

UI is clean and minimal, following Material Design principles

Open-source libraries have been used where appropriate

👨‍💻 Developed By

Nishant Kaushik
Android & Backend Developer
