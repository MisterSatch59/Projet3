# Projet3
Projet 3 du parcours "Expert Java EE" d'OpenClassrooms

GitHub : https://github.com/Oltenos/Projet3
RealTimeBord : https://realtimeboard.com/app/board/o9J_kz-ENlA=/

Afin d'utiliser l'application, merci de suivre le processus d'installation suivant (les éléments nécéssaires sont dans le dossier Installation)
1/ Mise en place de la base de données Postgresql
	- Création de la base de données "singescaladedb"
	- Création de l'utilisateur : "user" avec le mot de passe "user" (utilisé par l'application)
	- Création des tables contenant le jeu de demonstration avec le script sql creationBD.sql
OU
	- Restorer le fichier backup.sql dans PgAdmin

2/	Déployer le fichier projet-webapp.war dans tomcat
OU
	Charger le projet dans un IDE est utiliser le serveur tomcat embarqué

Remarques : 
Le jeux de données de démonstration contient 2 utilisateurs enregistrés dont voici les éléments de connexion à l'applicaton:
	SingEscalade (administrateur) / mdp : "singe00*"
	Oltenos / mdp : "azerty123*"

