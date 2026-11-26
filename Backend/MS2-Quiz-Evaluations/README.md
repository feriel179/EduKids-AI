# MS2 — Quiz & Évaluations (Spring Boot 4 + Swagger)

## Lancer
1. Démarrer MySQL (XAMPP/WAMP). La base `ms2_quiz_db` est créée automatiquement.
2. `./gradlew bootRun` (ou Run sur `WebDistribueApplication` dans IntelliJ, après "Reload Gradle Project").
3. Swagger : http://localhost:8082/swagger-ui.html

Un quiz de démo **publié** est créé au premier lancement : *Français — Grammaire (4ème primaire)*
(4 questions, 5 pts chacune). Désactivable avec `app.seed.enabled=false`.

## Eureka (Service Discovery)
MS2 s'enregistre dans le serveur Eureka de l'équipe (`Backend/Eureka`, port 8761) sous le nom **MS2-QUIZ**.
1. Lancer d'abord `Backend/Eureka` (EurekaApplication).
2. Lancer MS2 → il apparaît sur http://localhost:8761 dans « Instances currently registered with Eureka ».
3. Deuxième instance : IntelliJ → Run → Edit Configurations → Copy Configuration → VM options `-Dserver.port=8092`.

## Actuator (supervision)
| URL | Contenu |
|---|---|
| http://localhost:8082/actuator | Liste des endpoints exposés |
| http://localhost:8082/actuator/health | État : UP/DOWN, base MySQL, espace disque, Eureka |
| http://localhost:8082/actuator/info | Nom, version, responsable, version Java |
| http://localhost:8082/actuator/metrics | Métriques (ex. `/actuator/metrics/http.server.requests`) |
| http://localhost:8082/actuator/mappings | Tous les endpoints REST |

Eureka utilise `/actuator/health` (`eureka.client.healthcheck.enabled=true`) : si MySQL tombe, MS2 passe DOWN dans le dashboard.

## Architecture
```
entities/      Quiz, Question, Choix, Tentative, ReponseEleve (+ enums StatutQuiz, TypeQuestion)
repositories/  Spring Data JPA
dto/           records Request / Response (l'élève ne voit jamais les bonnes réponses)
services/      QuizService, QuestionService, TentativeService, CorrectionService, QuestionValidator, QuizMapper
controllers/   QuizController, QuestionController, TentativeController
exceptions/    404 / 400 avec message clair (GlobalExceptionHandler)
config/        OpenApiConfig (Swagger), DataInitializer (données de démo)
```

## Endpoints
| Méthode | URL | Rôle |
|---|---|---|
| POST | /api/quiz | Créer un quiz (BROUILLON), questions incluses ou non |
| GET | /api/quiz?chapitreId=&statut= | Lister / filtrer |
| GET | /api/quiz/{id} | Détail enseignant (avec bonnes réponses) |
| GET | /api/quiz/{id}/passer | Vue élève (sans bonnes réponses), quiz PUBLIE seulement |
| PUT | /api/quiz/{id} | Modifier (BROUILLON seulement) |
| PATCH | /api/quiz/{id}/statut?valeur=PUBLIE | BROUILLON / PUBLIE / ARCHIVE |
| DELETE | /api/quiz/{id} | Supprimer |
| GET/POST | /api/quiz/{quizId}/questions | Lister / ajouter une question |
| GET/PUT/DELETE | /api/questions/{id} | Question |
| POST | /api/questions/{id}/choix | Ajouter un choix |
| PUT/DELETE | /api/choix/{id} | Choix |
| POST | /api/quiz/{quizId}/tentatives | **Soumettre** → correction auto + note /20 |
| GET | /api/tentatives/{id} | Résultat détaillé |
| GET | /api/eleves/{eleveId}/tentatives?quizId= | **Historique** d'un élève |
| GET | /api/quiz/{quizId}/tentatives | Tentatives d'un quiz |
| GET | /api/quiz/{quizId}/statistiques | Moyenne, min, max, taux de réussite |
| DELETE | /api/tentatives/{id} | Supprimer une tentative |

## Règles métier
- Types : `QCM_UNIQUE` (1 bonne réponse), `QCM_MULTIPLE` (cocher toutes les bonnes et seulement elles),
  `VRAI_FAUX` (2 choix, 1 bon), `TEXTE_LIBRE` (les choix `estCorrect=true` = réponses acceptées ;
  comparaison sans majuscules / espaces / ponctuation finale).
- Notation tout ou rien par question ; `score` de la tentative = note sur 20.
- Un quiz n'est modifiable qu'en BROUILLON ; publication refusée si une question est incohérente ;
  retour en BROUILLON interdit dès qu'un élève l'a passé.
- `chapitreId` → MS1, `eleveId` → identité Keycloak (simples identifiants numériques ici).

## Scénario de test dans Swagger (exemple « Yasmine »)
1. `GET /api/quiz?statut=PUBLIE` → récupérer l'`id` du quiz Grammaire.
2. `GET /api/quiz/{id}/passer` → récupérer les `questionId` et `choixId`.
3. `POST /api/quiz/{id}/tentatives` :
```json
{
  "eleveId": "1",
  "reponses": [
    { "questionId": "<Q1>", "choixIds": ["<id de 'chevaux'>"] },
    { "questionId": "<Q2>", "choixIds": ["<id de 'Vrai'>"] },
    { "questionId": "<Q3>", "choixIds": ["<id de 'manger'>", "<id de 'courir'>"] },
    { "questionId": "<Q4>", "texteLibre": "sommes" }
  ]
}
```
→ note 20/20 + correction détaillée.
4. `GET /api/eleves/1/tentatives` → historique.

### Créer un quiz complet en une requête
```json
{
  "titre": "Maths — Additions",
  "chapitreId": 2,
  "dureeMin": 10,
  "questions": [
    { "enonce": "3 + 4 = ?", "type": "QCM_UNIQUE", "points": 2,
      "choix": [ {"texte": "6", "estCorrect": false}, {"texte": "7", "estCorrect": true} ] },
    { "enonce": "Écris en lettres : 10", "type": "TEXTE_LIBRE", "points": 2,
      "choix": [ {"texte": "dix", "estCorrect": true} ] }
  ]
}
```
Puis `PATCH /api/quiz/{id}/statut?valeur=PUBLIE`.
