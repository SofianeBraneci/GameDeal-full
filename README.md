# Important

## Backend
Pour executer le *backend*, assurez-vous que *Tomcat* soit à l'écoute des requêtes *HTTP* sur le port **8081**.

## Frontend
Pour executer le *frontend* il faut avoir préalablement installé **Nodejs** (minimum la version 16), ensuite, dans un terminal exécutez les commandes :
```
$ cd GameDeal
$ cd client
$ npm install
$ npm start
```

**npm install** installe toutes les dépendances incluses dans le ficher **package.json**.

**npm start** va deployer le client.

## Base de données
Vous trouverez un fichier *SQL* `gamedeals.sql` que vous pourez importer sur votre environnement de base de données dans le dossier `./docs`.

Une fois fait, modifiez le fichier `./src/db/DBStatic.java` pour faire en sorte que :

* **mysql_host** : correspond au port de votre serveur de bdd (**localhost:8889** par défaut).
* **mysql_db** : le nom de la base de donnée (**gamedeals** par défaut).
* **mysql_user** : votre user de connexion à votre serveur (**root** par défaut).
* **mysql_password** : votre mot de passe de connexion à votre serveur (**root** par défaut).

## Authentification
La base de données est préremplie avec des valeurs tests, une fois l'application lancée, vous pouvez vous loguer avec les identifiants :
> **Username** : adminGM

> **password** : admingGM
