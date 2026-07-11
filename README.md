# hello-world-maven (HW-maven)

Projet Java/Maven support des travaux pratiques **DevOps** (IF2I 1ère année) : il sert de base à une chaîne complète d'intégration et de déploiement continus construite au fil des TP — build automatisé, tests, analyse qualité, déploiement sur Tomcat, pipeline as code, agents distribués et sauvegardes.

## Contenu du projet

| Module | Description |
|---|---|
| `com.example.App` | Point d'entrée : démonstration des deux modules |
| `com.example.auth.AuthService` | Module d'authentification : inscription et vérification d'identifiants, mots de passe hachés en SHA-256 |
| `com.example.geometry.CircleCalculator` | Calcul du périmètre d'un cercle à partir du rayon ou du diamètre |
| `src/main/webapp` | Interface web JSP (`index.jsp`) déployée sur Tomcat : démo des deux modules avec formulaire de calcul |
| `src/test/java` | Tests unitaires JUnit 4 (14 tests) couvrant les deux modules |
| `docker/` | Infrastructure complète : `docker-compose.yml` (Jenkins, agent SSH, SonarQube, Tomcat) et image Tomcat personnalisée |
| `Jenkinsfile` | Pipeline d'intégration continue |

## Installation de l'infrastructure

Toute la chaîne CI/CD s'installe en une commande grâce à Docker Compose :

```bash
cd docker
cp .env.example .env      # renseigner JENKINS_AGENT_SSH_PUBKEY (clé publique de l'agent)
docker compose up -d
```

Les volumes portent des noms explicites (`jenkins_home`, `sonarqube_data`…) : une installation existante est réutilisée telle quelle (jobs, plugins, historique) ; sur une machine vierge, tout est recréé. La configuration à refaire dans ce dernier cas (plugins, outil `maven`, token SonarQube, credentials Tomcat, nœud agent, ThinBackup) est détaillée dans [docker/README.md](docker/README.md).

> Si les conteneurs d'origine créés à la main existent encore, les supprimer d'abord : `docker rm -f jenkins-v2 sonarqube tomcat jenkins-agent` (les volumes sont conservés).

## Build

Le projet est packagé en **WAR** (`target/hello-world.war`), compilé en Java 11.

```bash
mvn clean package
```

Les tests s'exécutent pendant le build ; la couverture de code est mesurée par **JaCoCo** et remontée dans **SonarQube**.

> Sur le poste de développement, Maven n'est pas installé localement : les builds sont exécutés par **Jenkins dans Docker**, déclenchés à chaque `git push`.

## Chaîne CI/CD

L'infrastructure tourne en conteneurs Docker sur le réseau `devops-network` :

- **Jenkins** (port 8080) — jobs freestyle, Maven et pipeline ; build automatique à chaque commit
- **SonarQube** (port 9000) — analyse qualité et quality gate (URL vue de Jenkins : `http://sonarqube:9000`)
- **Tomcat 9** (port 8081) — déploiement automatique du WAR via le plugin « Deploy to container » (URL vue de Jenkins : `http://tomcat:8080/manager`)
- **Agent SSH** `agent-linux` — exécution distribuée des builds
- **ThinBackup** — sauvegarde quotidienne de Jenkins, externalisée sur le PC hôte par bind mount

Après déploiement, l'application est accessible sur : `http://localhost:8081/hello-world`

## Points de vigilance retenus des TP

- Entre conteneurs, on se joint par **nom de conteneur**, jamais par `localhost`.
- Les mots de passe (Tomcat, SonarQube) passent par les **credentials Jenkins**, jamais en dur.
- En cas d'échec des tests, le build échoue et **aucun déploiement n'a lieu**.
- Le nom d'outil (`tools { maven '...' }`) du Jenkinsfile doit correspondre **exactement** à la Global Tool Configuration.

## Rapport de TP

Le rapport complet illustré des neuf TP est disponible à la racine du dépôt :

- [rapport.md](rapport.md) — version Markdown (captures d'écran dans [`assets/`](assets/))
- [rapport.pdf](rapport.pdf) — version PDF
