
# ☁️ Wetteranwendung – *Interstellar* 

![Wetterbild](images/w-2.jpg)

> „Zeit ist relativ. Eine Stunde auf einem Planeten kann auf der Erde Jahre bedeuten. Aber das Wetter... ist überall spürbar.“

In **Interstellar** sucht die Menschheit nach einer neuen Heimat – einem Ort mit atembarer Luft und aufgehender Sonne. Jeder Sturm, jede Wolke markiert die feine Grenze zwischen Leben und Auslöschung. Deshalb ist das Wetter nicht nur ein Datensatz, sondern ein **Signal des Überlebens**.

In diesem Projekt betreten wir – genau wie Cooper und seine Crew – das Unbekannte:  
Wir greifen über APIs auf Echtzeit-Wetterdaten zu, um unseren eigenen Planeten besser zu verstehen.  
Jede Codezeile bringt mehr Klarheit, Sicherheit und Bewusstsein.

> Denn manchmal braucht es Wissenschaft, nur um einen Schritt nach draußen zu wagen. 😊
 
## 🧭 Architekturübersicht – Open Weather API  
![Wetterbild](images/w-1.jpg)
#### Diese Anwendung basiert auf einer Microservice-Architektur mit Spring Boot, Docker, OpenWeatherStack API, Spring Cache, Resilience4j, Prometheus und Grafana. Unten findest du eine vollständige Analyse der Architekturkomponenten:

###  1. Eingangspunkt: WeatherAPI
	•	Endpunkt: GET /api/open-weather/{city}
	•	Diese Schicht empfängt Anfragen vom Client.
	•	Integriert mit Resilience4j RateLimiter → Beschränkt auf 10 Anfragen pro Minute.
	•	Führt CityNameValidator durch, um ungültige Eingaben zu vermeiden.
	•	Delegiert die Anfrage an WeatherService. 
___ 
###  2. WeatherService – Geschäftslogik
	•	Verarbeitet die Anfrage und entscheidet:
	1.	Ist die Stadt bereits in der Datenbank vorhanden?
	2.	Ist der letzte Datensatz aktuell?
	•	Falls nicht aktuell oder nicht vorhanden:
	•	Ruft neue Wetterdaten über WeatherStack API ab.
	•	Speichert oder aktualisiert sie in der H2 In-Memory-Datenbank über WeatherRepository. 
___ 
###  3. WeatherRepository & WeatherDB
	•	Speichert Wetterdaten in einer eingebetteten H2-Datenbank.
	•	Persistiert WeatherEntity, die folgende Felder enthält:
	•	id
	•	requestedCityName
	•	cityName
	•	country
	•	temperature
	•	updateTime
	•	responseLocalTime 
___ 
###  4. Spring Cache & Scheduled TTL
	•	Nutzt Spring Cache, um wiederholte Anfragen nach derselben Stadt effizient zu bedienen.
	•	Ein geplanter (@Scheduled) Task leert den Cache regelmäßig basierend auf der TTL. 

___ 
###  5. Externer API-Zugriff – WeatherStack
	•	Wetterdaten werden dynamisch über HTTP GET mit dem konfigurierten API-Key aus application.properties bezogen.
	•	Beispiel: http://api.weatherstack.com/current?access_key=API_KEY&query=cityName


# 🌦️ Weather Application (Spring Boot + PostgreSQL + Docker)

A simple but powerful weather forecast API built with **Spring Boot**, **PostgreSQL**, and **WeatherStack API**, containerized using **Docker**.


---

## 🚀 Features

- 🌍 Get current weather data for any city
- 🐳 Run with Docker & PostgreSQL container
- 🧱 Uses external API (WeatherStack) to fetch real-time data
- 🛠️ Clean layered architecture (Controller → Service → API Client)
- 📄 Environment variable-based config (production-ready)
- 📚 Custom schema support for PostgreSQL
- 🌐 RESTful endpoints

---

## 📦 Tech Stack

| Layer              | Technology            |
|-------------------|------------------------|
| 🧠 Language        | Java 17                |
| 🚀 Framework       | Spring Boot 3.4.4       |
| 🌐 REST API        | Spring Web             |
| 📊 Database        | PostgreSQL             |
| 🐘 ORM             | Spring Data JPA        |
| 🔐 Config          | Environment Variables  |
| 📦 External API    | WeatherStack           |
| 🐳 DevOps          | Docker, Docker Compose |

---
📚 Quellen (Kaynakça)

- https://www.youtube.com/watch?v=i57VVwXPCX4&list=PLCp1YoRkzkpayOOFZy6c7WARJ7Adfruju&index=14 
- https://weatherstack.com/
- https://www.udemy.com/course/sifirdan-ileri-seviye-spring-kursu/learn/lecture/46192207#overview
