# EduKids AI 🎓

**Plateforme éducative intelligente pour les élèves de la 1ère à la 6ème année primaire.**

## 📌 Présentation du projet

EduKids AI est une plateforme éducative moderne qui vise à faciliter l’apprentissage des élèves du primaire en Tunisie.

Elle regroupe des cours, des ressources pédagogiques, des quiz interactifs, une marketplace de livres scolaires d’occasion, des défis éducatifs et des événements avec QR Code.

Grâce à l’intelligence artificielle, elle propose des recommandations personnalisées pour accompagner chaque élève dans son apprentissage.

## 🧩 Architecture microservices

Le projet repose sur six microservices :

| Microservice | Fonctionnalité | Responsable |
|---|---|---|
| MS1 – Cours & Ressources | Gestion des cours et supports pédagogiques | Feriel Souissi |
| MS2 – Quiz & Évaluations | Quiz, corrections et notes | Aziz Latrache |
| MS3 – Marketplace | Achat et vente de livres scolaires | Marwen Hamrouni |
| MS4 – Gamification | Points, badges et défis | Rami Ben Mohamed |
| MS5 – Événements & QR Code | Gestion des événements et inscriptions | Énermine Chakroun |
| MS6 – IA collective | Recommandations éducatives personnalisées | Toute l’équipe |

## 🛠️ Technologies proposées

- **Frontend :** Angular
- **Backend :** Spring Boot
- **Microservice IA :** Python / FastAPI (à confirmer)
- **Bases de données :** MySQL et PostgreSQL
- **API Gateway :** Spring Cloud Gateway
- **Service Discovery :** Eureka
- **Sécurité :** Keycloak
- **Communication :** REST / OpenFeign et RabbitMQ
- **Conteneurisation :** Docker Compose

## 📂 Organisation du dépôt

```text
EduKids-AI/
├── Backend/
│   ├── MS1-Cours-Ressources/
│   ├── MS2-Quiz-Evaluations/
│   ├── MS3-Marketplace/
│   ├── MS4-Gamification/
│   ├── MS5-Evenements-QR/
│   └── MS6-IA-Collective/
├── Frontend/
├── Documentation/
│   ├── Diagrams/
│   └── Presentation/
└── README.md
```

## 🎯 Objectif

Créer un environnement éducatif accessible, interactif et personnalisé qui encourage les élèves à apprendre, progresser et s’amuser.

**Projet académique — Applications Web Distribuées (AWD).**
