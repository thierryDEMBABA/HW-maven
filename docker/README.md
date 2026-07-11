# Infrastructure Docker des TP DevOps

Ce dossier permet de réinstaller toute la chaîne CI/CD (Jenkins, agent SSH, SonarQube, Tomcat) en une commande, sur le réseau `devops-network`.

## Réinstallation rapide

```bash
cd docker
cp .env.example .env      # puis renseigner JENKINS_AGENT_SSH_PUBKEY (voir plus bas)
docker compose up -d
```

Les volumes portent des **noms explicites** (`jenkins_home`, `sonarqube_data`…) : si les volumes de l'ancienne installation existent encore, ils sont réutilisés tels quels — jobs, plugins et historique Jenkins sont retrouvés. Sur une machine vierge, ils sont créés vides.

| Service | URL locale | URL vue de Jenkins |
|---|---|---|
| Jenkins | http://localhost:8080 | — |
| SonarQube | http://localhost:9000 | `http://sonarqube:9000` |
| Tomcat (application) | http://localhost:8081/hello-world | `http://tomcat:8080/manager` |

## Configuration à refaire après une installation vierge

Si les volumes n'existent plus (nouvelle machine), reconfigurer dans Jenkins :

1. **Premier démarrage** : mot de passe initial dans `docker logs jenkins-v2`, installer les plugins suggérés + `Deploy to container`, `SonarQube Scanner`, `ThinBackup`.
2. **Outil Maven** : Administrer Jenkins → Tools → installation Maven nommée exactement `maven` (le Jenkinsfile y fait référence).
3. **SonarQube** : générer un token dans SonarQube (admin/admin au premier démarrage) puis le déclarer dans Administrer Jenkins → System → SonarQube servers, URL `http://sonarqube:9000`.
4. **Credentials Tomcat** : username/password `admin` / `admin123` (définis dans `tomcat/tomcat-users.xml`) pour l'action post-build « Deploy war/ear to a container ».
5. **Agent SSH** (`agent-linux`) :
   - générer une paire de clés : `ssh-keygen -t rsa -f jenkins_agent_key` ;
   - mettre la **clé publique** dans `docker/.env` (`JENKINS_AGENT_SSH_PUBKEY=...`) puis `docker compose up -d jenkins-agent` ;
   - déclarer la **clé privée** dans les credentials Jenkins et créer le nœud `agent-linux` (hôte `jenkins-agent`, port 22).
6. **ThinBackup** : répertoire `/mnt/backups` (déjà monté vers `../../jenkins-backups` sur le PC), planification `H 23 * * *`.

## Contenu

- `docker-compose.yml` — les 4 services, le réseau et les volumes nommés ;
- `.env.example` — modèle pour la clé publique de l'agent (copier vers `.env`, jamais commité) ;
- `tomcat/` — image Tomcat personnalisée : manager activé, utilisateur `manager-script`, accès distant au manager autorisé pour Jenkins.
