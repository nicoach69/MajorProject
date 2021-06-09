# MajorProject
FireEmergency

### Spécifications pour le test :
1 Importer tout les projets Maeven
2 Ouvrir le fichier index.html dans le dossier Front
3 Lancer tout les service SAUF THREAD
4 A l'aide du terminal linux :
cd /tmp/
git clone https://gitlab.com/js-project-gis-1/js-fire-simulator-starter.git
newgrp - docker
cd /tmp/js-fire-simulator-starter/
docker-compose up
5 Lancer le service thread
6 Ajouter quelques véhicules
7 Envoyer la requête PUT http://localhost:8081/config/creation à l'aide de Postman contenant le Body JSON suivant :
{
    "fireCreationProbability": 1.0,
    "fireCreationSleep": 10000,
    "fireCreationZone": [
        {
            "type": "Point",
            "coordinates": [
                520820,
                5719535
            ]
        },
        {
            "type": "Point",
            "coordinates": [
                566984,
                5754240
            ]
        }
    ],
    "max_INTENSITY": 50.0,
    "max_RANGE": 50.0
}

Observer
