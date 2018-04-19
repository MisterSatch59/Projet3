--CREATION TABLES

CREATE TABLE public.photo (
                id INTEGER NOT NULL,
                nom_fichier VARCHAR(100) NOT NULL,
                CONSTRAINT photo_pk PRIMARY KEY (id)
);


CREATE SEQUENCE public.zone_texte_id_seq;

CREATE TABLE public.zone_texte (
                id INTEGER NOT NULL DEFAULT nextval('public.zone_texte_id_seq'),
                titre VARCHAR(100) NOT NULL,
                CONSTRAINT zone_texte_pk PRIMARY KEY (id)
);


ALTER SEQUENCE public.zone_texte_id_seq OWNED BY public.zone_texte.id;

CREATE SEQUENCE public.paragraphe_id_seq;

CREATE TABLE public.paragraphe (
                id INTEGER NOT NULL DEFAULT nextval('public.paragraphe_id_seq'),
                texte VARCHAR(1000) NOT NULL,
                zone_texte_id INTEGER NOT NULL,
                num_ordre SMALLINT NOT NULL,
                CONSTRAINT paragraphe_pk PRIMARY KEY (id)
);


ALTER SEQUENCE public.paragraphe_id_seq OWNED BY public.paragraphe.id;

CREATE UNIQUE INDEX paragraphe_idx
 ON public.paragraphe
 ( zone_texte_id ASC, num_ordre ASC );

CLUSTER paragraphe_idx ON paragraphe;

CREATE TABLE public.topo (
                titre VARCHAR(100) NOT NULL,
                description_id INTEGER NOT NULL,
                CONSTRAINT topo_pk PRIMARY KEY (titre)
);


CREATE TABLE public.photo_topo (
                titre_topo VARCHAR(100) NOT NULL,
                photo_id INTEGER NOT NULL,
                CONSTRAINT photo_topo_pk PRIMARY KEY (titre_topo, photo_id)
);


CREATE TABLE public.utilisateur (
                pseudo VARCHAR(30) NOT NULL,
                mail VARCHAR(200) NOT NULL,
                mdp VARCHAR(100) NOT NULL,
                avatar VARCHAR(100),
                admin BOOLEAN NOT NULL,
                CONSTRAINT utilisateur_pk PRIMARY KEY (pseudo)
);


CREATE SEQUENCE public.exemplaire_topo_id_seq;

CREATE TABLE public.exemplaire_topo (
                id INTEGER NOT NULL DEFAULT nextval('public.exemplaire_topo_id_seq'),
                titre_topo VARCHAR(100) NOT NULL,
                pseudo_proprietaire VARCHAR(30) NOT NULL,
                condition_id INTEGER NOT NULL,
                CONSTRAINT exemplaire_topo_pk PRIMARY KEY (id)
);


ALTER SEQUENCE public.exemplaire_topo_id_seq OWNED BY public.exemplaire_topo.id;

CREATE TABLE public.emprunt (
                pseudo_emprunteur VARCHAR(30) NOT NULL,
                exemplaire_topo_id INTEGER NOT NULL,
                debut DATE NOT NULL,
                fin DATE NOT NULL,
                CONSTRAINT emprunt_pk PRIMARY KEY (pseudo_emprunteur, exemplaire_topo_id)
);


CREATE TABLE public.difficulte (
                nom VARCHAR(2) NOT NULL,
                CONSTRAINT difficulte_pk PRIMARY KEY (nom)
);


CREATE TABLE public.profil (
                nom VARCHAR(10) NOT NULL,
                CONSTRAINT profil_pk PRIMARY KEY (nom)
);


CREATE TABLE public.orientation (
                nom VARCHAR(2) NOT NULL,
                CONSTRAINT orientation_pk PRIMARY KEY (nom)
);


CREATE TABLE public.type (
                nom VARCHAR(10) NOT NULL,
                CONSTRAINT type_pk PRIMARY KEY (nom)
);


CREATE TABLE public.departement (
                numero VARCHAR(3) NOT NULL,
                nom VARCHAR(40) NOT NULL,
                CONSTRAINT departement_pk PRIMARY KEY (numero)
);


CREATE SEQUENCE public.ville_id_seq;

CREATE TABLE public.ville (
                id INTEGER NOT NULL DEFAULT nextval('public.ville_id_seq'),
                cp VARCHAR(5) NOT NULL,
                departement VARCHAR(3) NOT NULL,
                nom VARCHAR(40) NOT NULL,
                CONSTRAINT ville_pk PRIMARY KEY (id)
);


ALTER SEQUENCE public.ville_id_seq OWNED BY public.ville.id;

CREATE SEQUENCE public.spot_id_seq;

CREATE TABLE public.spot (
                id INTEGER NOT NULL DEFAULT nextval('public.spot_id_seq'),
                nom VARCHAR(40) NOT NULL,
                pseudo_auteur VARCHAR(30) NOT NULL,
                ouvert BOOLEAN NOT NULL,
                adapte_enfants BOOLEAN,
                latitude VARCHAR(15),
                longitude VARCHAR(15),
                ville_id INTEGER NOT NULL,
                presentation_id INTEGER,
                nb_secteur SMALLINT,
                nb_voie VARCHAR(50) NOT NULL,
                hauteur_min SMALLINT,
                hauteur_max SMALLINT,
                difficulte_min VARCHAR(2) NOT NULL,
                difficulte_max VARCHAR(2) NOT NULL,
                CONSTRAINT spot_pk PRIMARY KEY (id)
);


ALTER SEQUENCE public.spot_id_seq OWNED BY public.spot.id;

CREATE TABLE public.photo_spot (
                spot_id INTEGER NOT NULL,
                photo_id INTEGER NOT NULL,
                CONSTRAINT photo_spot_pk PRIMARY KEY (spot_id, photo_id)
);


CREATE TABLE public.spot_profil (
                spot_id INTEGER NOT NULL,
                profil VARCHAR(10) NOT NULL,
                CONSTRAINT spot_profil_pk PRIMARY KEY (spot_id, profil)
);


CREATE TABLE public.spot_orientation (
                spot_id INTEGER NOT NULL,
                orientation VARCHAR(2) NOT NULL,
                CONSTRAINT spot_orientation_pk PRIMARY KEY (spot_id, orientation)
);


CREATE TABLE public.spot_type (
                type VARCHAR(10) NOT NULL,
                spot_id INTEGER NOT NULL,
                CONSTRAINT spot_type_pk PRIMARY KEY (type, spot_id)
);


CREATE TABLE public.spot_topo (
                spot_id INTEGER NOT NULL,
                titre VARCHAR(100) NOT NULL,
                CONSTRAINT spot_topo_pk PRIMARY KEY (spot_id, titre)
);


CREATE TABLE public.commentaire (
                id INTEGER NOT NULL,
                date TIMESTAMP NOT NULL,
                pseudo_auteur VARCHAR(30) NOT NULL,
                alerte BOOLEAN NOT NULL,
                spot_id INTEGER NOT NULL,
                CONSTRAINT commentaire_pk PRIMARY KEY (id)
);


CREATE INDEX commentaire_idx
 ON public.commentaire
 ( date DESC );

CLUSTER commentaire_idx ON commentaire;

ALTER TABLE public.photo_spot ADD CONSTRAINT photo_photo_spot_fk
FOREIGN KEY (photo_id)
REFERENCES public.photo (id)
ON DELETE NO ACTION
ON UPDATE NO ACTION
NOT DEFERRABLE;

ALTER TABLE public.photo_topo ADD CONSTRAINT photo_photo_topo_fk
FOREIGN KEY (photo_id)
REFERENCES public.photo (id)
ON DELETE NO ACTION
ON UPDATE NO ACTION
NOT DEFERRABLE;

ALTER TABLE public.paragraphe ADD CONSTRAINT zone_texte_paragraphe_fk
FOREIGN KEY (zone_texte_id)
REFERENCES public.zone_texte (id)
ON DELETE NO ACTION
ON UPDATE NO ACTION
NOT DEFERRABLE;

ALTER TABLE public.exemplaire_topo ADD CONSTRAINT zone_texte_exemplaire_topo_fk
FOREIGN KEY (condition_id)
REFERENCES public.zone_texte (id)
ON DELETE NO ACTION
ON UPDATE NO ACTION
NOT DEFERRABLE;

ALTER TABLE public.topo ADD CONSTRAINT zone_texte_topo_fk
FOREIGN KEY (description_id)
REFERENCES public.zone_texte (id)
ON DELETE NO ACTION
ON UPDATE NO ACTION
NOT DEFERRABLE;

ALTER TABLE public.spot ADD CONSTRAINT zone_texte_spot_fk
FOREIGN KEY (presentation_id)
REFERENCES public.zone_texte (id)
ON DELETE NO ACTION
ON UPDATE NO ACTION
NOT DEFERRABLE;

ALTER TABLE public.commentaire ADD CONSTRAINT zone_texte_commentaire_fk
FOREIGN KEY (id)
REFERENCES public.zone_texte (id)
ON DELETE NO ACTION
ON UPDATE NO ACTION
NOT DEFERRABLE;

ALTER TABLE public.spot_topo ADD CONSTRAINT topo_spot_topo_fk
FOREIGN KEY (titre)
REFERENCES public.topo (titre)
ON DELETE NO ACTION
ON UPDATE NO ACTION
NOT DEFERRABLE;

ALTER TABLE public.exemplaire_topo ADD CONSTRAINT topo_exemplaire_topo_fk
FOREIGN KEY (titre_topo)
REFERENCES public.topo (titre)
ON DELETE NO ACTION
ON UPDATE NO ACTION
NOT DEFERRABLE;

ALTER TABLE public.photo_topo ADD CONSTRAINT topo_photo_topo_fk
FOREIGN KEY (titre_topo)
REFERENCES public.topo (titre)
ON DELETE NO ACTION
ON UPDATE NO ACTION
NOT DEFERRABLE;

ALTER TABLE public.spot ADD CONSTRAINT utilisateur_spot_fk
FOREIGN KEY (pseudo_auteur)
REFERENCES public.utilisateur (pseudo)
ON DELETE NO ACTION
ON UPDATE NO ACTION
NOT DEFERRABLE;

ALTER TABLE public.commentaire ADD CONSTRAINT utilisateur_commentaire_fk
FOREIGN KEY (pseudo_auteur)
REFERENCES public.utilisateur (pseudo)
ON DELETE NO ACTION
ON UPDATE NO ACTION
NOT DEFERRABLE;

ALTER TABLE public.emprunt ADD CONSTRAINT utilisateur_emprunt_fk
FOREIGN KEY (pseudo_emprunteur)
REFERENCES public.utilisateur (pseudo)
ON DELETE NO ACTION
ON UPDATE NO ACTION
NOT DEFERRABLE;

ALTER TABLE public.exemplaire_topo ADD CONSTRAINT utilisateur_exemplaire_topo_fk
FOREIGN KEY (pseudo_proprietaire)
REFERENCES public.utilisateur (pseudo)
ON DELETE NO ACTION
ON UPDATE NO ACTION
NOT DEFERRABLE;

ALTER TABLE public.emprunt ADD CONSTRAINT exemplaire_topo_emprunt_fk
FOREIGN KEY (exemplaire_topo_id)
REFERENCES public.exemplaire_topo (id)
ON DELETE NO ACTION
ON UPDATE NO ACTION
NOT DEFERRABLE;

ALTER TABLE public.spot ADD CONSTRAINT difficulte_spot_fk
FOREIGN KEY (difficulte_min)
REFERENCES public.difficulte (nom)
ON DELETE NO ACTION
ON UPDATE NO ACTION
NOT DEFERRABLE;

ALTER TABLE public.spot ADD CONSTRAINT difficulte_spot_fk1
FOREIGN KEY (difficulte_max)
REFERENCES public.difficulte (nom)
ON DELETE NO ACTION
ON UPDATE NO ACTION
NOT DEFERRABLE;

ALTER TABLE public.spot_profil ADD CONSTRAINT profil_spot_profil_fk
FOREIGN KEY (profil)
REFERENCES public.profil (nom)
ON DELETE NO ACTION
ON UPDATE NO ACTION
NOT DEFERRABLE;

ALTER TABLE public.spot_orientation ADD CONSTRAINT orientation_spot_orientation_fk
FOREIGN KEY (orientation)
REFERENCES public.orientation (nom)
ON DELETE NO ACTION
ON UPDATE NO ACTION
NOT DEFERRABLE;

ALTER TABLE public.spot_type ADD CONSTRAINT type_type_spot_fk
FOREIGN KEY (type)
REFERENCES public.type (nom)
ON DELETE NO ACTION
ON UPDATE NO ACTION
NOT DEFERRABLE;

ALTER TABLE public.ville ADD CONSTRAINT departement_ville_fk
FOREIGN KEY (departement)
REFERENCES public.departement (numero)
ON DELETE NO ACTION
ON UPDATE NO ACTION
NOT DEFERRABLE;

ALTER TABLE public.spot ADD CONSTRAINT ville_spot_fk
FOREIGN KEY (ville_id)
REFERENCES public.ville (id)
ON DELETE NO ACTION
ON UPDATE NO ACTION
NOT DEFERRABLE;

ALTER TABLE public.commentaire ADD CONSTRAINT spot_commentaire_fk
FOREIGN KEY (spot_id)
REFERENCES public.spot (id)
ON DELETE NO ACTION
ON UPDATE NO ACTION
NOT DEFERRABLE;

ALTER TABLE public.spot_topo ADD CONSTRAINT spot_spot_topo_fk
FOREIGN KEY (spot_id)
REFERENCES public.spot (id)
ON DELETE NO ACTION
ON UPDATE NO ACTION
NOT DEFERRABLE;

ALTER TABLE public.spot_type ADD CONSTRAINT spot_type_spot_fk
FOREIGN KEY (spot_id)
REFERENCES public.spot (id)
ON DELETE NO ACTION
ON UPDATE NO ACTION
NOT DEFERRABLE;

ALTER TABLE public.spot_orientation ADD CONSTRAINT spot_spot_orientation_fk
FOREIGN KEY (spot_id)
REFERENCES public.spot (id)
ON DELETE NO ACTION
ON UPDATE NO ACTION
NOT DEFERRABLE;

ALTER TABLE public.spot_profil ADD CONSTRAINT spot_spot_profil_fk
FOREIGN KEY (spot_id)
REFERENCES public.spot (id)
ON DELETE NO ACTION
ON UPDATE NO ACTION
NOT DEFERRABLE;

ALTER TABLE public.photo_spot ADD CONSTRAINT spot_photo_spot_fk
FOREIGN KEY (spot_id)
REFERENCES public.spot (id)
ON DELETE NO ACTION
ON UPDATE NO ACTION
NOT DEFERRABLE;

--INSERTION DES DONNEES
INSERT INTO public.type (nom) VALUES ('Falaise');
INSERT INTO public.type (nom) VALUES ('Bloc');

INSERT INTO public.orientation (nom) VALUES ('N');
INSERT INTO public.orientation (nom) VALUES ('NE');
INSERT INTO public.orientation (nom) VALUES ('NW');
INSERT INTO public.orientation (nom) VALUES ('S');
INSERT INTO public.orientation (nom) VALUES ('SE');
INSERT INTO public.orientation (nom) VALUES ('SW');
INSERT INTO public.orientation (nom) VALUES ('E');
INSERT INTO public.orientation (nom) VALUES ('W');

INSERT INTO public.profil (nom) VALUES ('Dévers');
INSERT INTO public.profil (nom) VALUES ('Vertical');
INSERT INTO public.profil (nom) VALUES ('Dalle');
INSERT INTO public.profil (nom) VALUES ('Surplomb');


INSERT INTO public.difficulte (nom) VALUES ('3a');
INSERT INTO public.difficulte (nom) VALUES ('3b');
INSERT INTO public.difficulte (nom) VALUES ('3c');
INSERT INTO public.difficulte (nom) VALUES ('4a');
INSERT INTO public.difficulte (nom) VALUES ('4b');
INSERT INTO public.difficulte (nom) VALUES ('4c');
INSERT INTO public.difficulte (nom) VALUES ('5a');
INSERT INTO public.difficulte (nom) VALUES ('5b');
INSERT INTO public.difficulte (nom) VALUES ('5c');
INSERT INTO public.difficulte (nom) VALUES ('6a');
INSERT INTO public.difficulte (nom) VALUES ('6b');
INSERT INTO public.difficulte (nom) VALUES ('6c');
INSERT INTO public.difficulte (nom) VALUES ('7a');
INSERT INTO public.difficulte (nom) VALUES ('7b');
INSERT INTO public.difficulte (nom) VALUES ('7c');
INSERT INTO public.difficulte (nom) VALUES ('8a');
INSERT INTO public.difficulte (nom) VALUES ('8b');
INSERT INTO public.difficulte (nom) VALUES ('8c');
INSERT INTO public.difficulte (nom) VALUES ('9a');
INSERT INTO public.difficulte (nom) VALUES ('9b');
INSERT INTO public.difficulte (nom) VALUES ('9c');

INSERT INTO public.departement (numero,nom) VALUES ('1','Ain');
INSERT INTO public.departement (numero,nom) VALUES ('2','Aisne');
INSERT INTO public.departement (numero,nom) VALUES ('3','Allier');
INSERT INTO public.departement (numero,nom) VALUES ('4','Alpes de Haute-Provence');
INSERT INTO public.departement (numero,nom) VALUES ('5','Hautes-Alpes');
INSERT INTO public.departement (numero,nom) VALUES ('6','Alpes-Maritimes');
INSERT INTO public.departement (numero,nom) VALUES ('7','Ardèche');
INSERT INTO public.departement (numero,nom) VALUES ('8','Ardennes');
INSERT INTO public.departement (numero,nom) VALUES ('9','Ariège');
INSERT INTO public.departement (numero,nom) VALUES ('10','Aube');
INSERT INTO public.departement (numero,nom) VALUES ('11','Aude');
INSERT INTO public.departement (numero,nom) VALUES ('12','Aveyron');
INSERT INTO public.departement (numero,nom) VALUES ('13','Bouches du Rhône');
INSERT INTO public.departement (numero,nom) VALUES ('14','Calvados');
INSERT INTO public.departement (numero,nom) VALUES ('15','Cantal');
INSERT INTO public.departement (numero,nom) VALUES ('16','Charente');
INSERT INTO public.departement (numero,nom) VALUES ('17','Charente Maritime');
INSERT INTO public.departement (numero,nom) VALUES ('18','Cher');
INSERT INTO public.departement (numero,nom) VALUES ('19','Corrèze');
INSERT INTO public.departement (numero,nom) VALUES ('21','Côte d''Or');
INSERT INTO public.departement (numero,nom) VALUES ('22','Côtes d''Armor');
INSERT INTO public.departement (numero,nom) VALUES ('23','Creuse');
INSERT INTO public.departement (numero,nom) VALUES ('24','Dordogne');
INSERT INTO public.departement (numero,nom) VALUES ('25','Doubs');
INSERT INTO public.departement (numero,nom) VALUES ('26','Drôme');
INSERT INTO public.departement (numero,nom) VALUES ('27','Eure');
INSERT INTO public.departement (numero,nom) VALUES ('28','Eure-et-Loir');
INSERT INTO public.departement (numero,nom) VALUES ('29','Finistère');
INSERT INTO public.departement (numero,nom) VALUES ('30','Gard');
INSERT INTO public.departement (numero,nom) VALUES ('31','Haute-Garonne');
INSERT INTO public.departement (numero,nom) VALUES ('32','Gers');
INSERT INTO public.departement (numero,nom) VALUES ('33','Gironde');
INSERT INTO public.departement (numero,nom) VALUES ('34','Hérault');
INSERT INTO public.departement (numero,nom) VALUES ('35','Ille-et-Vilaine');
INSERT INTO public.departement (numero,nom) VALUES ('36','Indre');
INSERT INTO public.departement (numero,nom) VALUES ('37','Indre-et-Loire');
INSERT INTO public.departement (numero,nom) VALUES ('38','Isère');
INSERT INTO public.departement (numero,nom) VALUES ('39','Jura');
INSERT INTO public.departement (numero,nom) VALUES ('40','Landes');
INSERT INTO public.departement (numero,nom) VALUES ('41','Loir-et-Cher');
INSERT INTO public.departement (numero,nom) VALUES ('42','Loire');
INSERT INTO public.departement (numero,nom) VALUES ('43','Haute-Loire');
INSERT INTO public.departement (numero,nom) VALUES ('44','Loire-Atlantique');
INSERT INTO public.departement (numero,nom) VALUES ('45','Loiret');
INSERT INTO public.departement (numero,nom) VALUES ('46','Lot');
INSERT INTO public.departement (numero,nom) VALUES ('47','Lot-et-Garonne');
INSERT INTO public.departement (numero,nom) VALUES ('48','Lozère');
INSERT INTO public.departement (numero,nom) VALUES ('49','Maine-et-Loire');
INSERT INTO public.departement (numero,nom) VALUES ('50','Manche');
INSERT INTO public.departement (numero,nom) VALUES ('51','Marne');
INSERT INTO public.departement (numero,nom) VALUES ('52','Haute-Marne');
INSERT INTO public.departement (numero,nom) VALUES ('53','Mayenne');
INSERT INTO public.departement (numero,nom) VALUES ('54','Meurthe-et-Moselle');
INSERT INTO public.departement (numero,nom) VALUES ('55','Meuse');
INSERT INTO public.departement (numero,nom) VALUES ('56','Morbihan');
INSERT INTO public.departement (numero,nom) VALUES ('57','Moselle');
INSERT INTO public.departement (numero,nom) VALUES ('58','Nièvre');
INSERT INTO public.departement (numero,nom) VALUES ('59','Nord');
INSERT INTO public.departement (numero,nom) VALUES ('60','Oise');
INSERT INTO public.departement (numero,nom) VALUES ('61','Orne');
INSERT INTO public.departement (numero,nom) VALUES ('62','Pas-de-Calais');
INSERT INTO public.departement (numero,nom) VALUES ('63','Puy-de-Dôme');
INSERT INTO public.departement (numero,nom) VALUES ('64','Pyrénées-Atlantiques');
INSERT INTO public.departement (numero,nom) VALUES ('65','Hautes-Pyrénées');
INSERT INTO public.departement (numero,nom) VALUES ('66','Pyrénées-Orientales');
INSERT INTO public.departement (numero,nom) VALUES ('67','Bas-Rhin');
INSERT INTO public.departement (numero,nom) VALUES ('68','Haut-Rhin');
INSERT INTO public.departement (numero,nom) VALUES ('69','Rhône');
INSERT INTO public.departement (numero,nom) VALUES ('70','Haute-Saône');
INSERT INTO public.departement (numero,nom) VALUES ('71','Saône-et-Loire');
INSERT INTO public.departement (numero,nom) VALUES ('72','Sarthe');
INSERT INTO public.departement (numero,nom) VALUES ('73','Savoie');
INSERT INTO public.departement (numero,nom) VALUES ('74','Haute-Savoie');
INSERT INTO public.departement (numero,nom) VALUES ('75','Paris');
INSERT INTO public.departement (numero,nom) VALUES ('76','Seine-Maritime');
INSERT INTO public.departement (numero,nom) VALUES ('77','Seine-et-Marne');
INSERT INTO public.departement (numero,nom) VALUES ('78','Yvelines');
INSERT INTO public.departement (numero,nom) VALUES ('79','Deux-Sèvres');
INSERT INTO public.departement (numero,nom) VALUES ('80','Somme');
INSERT INTO public.departement (numero,nom) VALUES ('81','Tarn');
INSERT INTO public.departement (numero,nom) VALUES ('82','Tarn-et-Garonne');
INSERT INTO public.departement (numero,nom) VALUES ('83','Var');
INSERT INTO public.departement (numero,nom) VALUES ('84','Vaucluse');
INSERT INTO public.departement (numero,nom) VALUES ('85','Vendée');
INSERT INTO public.departement (numero,nom) VALUES ('86','Vienne');
INSERT INTO public.departement (numero,nom) VALUES ('87','Haute-Vienne');
INSERT INTO public.departement (numero,nom) VALUES ('88','Vosges');
INSERT INTO public.departement (numero,nom) VALUES ('89','Yonne');
INSERT INTO public.departement (numero,nom) VALUES ('90','Territoire-de-Belfort');
INSERT INTO public.departement (numero,nom) VALUES ('91','Essonne');
INSERT INTO public.departement (numero,nom) VALUES ('92','Hauts-de-Seine');
INSERT INTO public.departement (numero,nom) VALUES ('93','Seine-St-Denis');
INSERT INTO public.departement (numero,nom) VALUES ('94','Val-de-Marne');
INSERT INTO public.departement (numero,nom) VALUES ('95','Val-d''Oise');
INSERT INTO public.departement (numero,nom) VALUES ('2A','Corse du Sud');
INSERT INTO public.departement (numero,nom) VALUES ('2B','Haute-Corse');

INSERT INTO public.departement (numero,nom) VALUES ('971','Guadeloupe');
INSERT INTO public.departement (numero,nom) VALUES ('972','Martinique');
INSERT INTO public.departement (numero,nom) VALUES ('973','Guyane');
INSERT INTO public.departement (numero,nom) VALUES ('974','La Réunion');
INSERT INTO public.departement (numero,nom) VALUES ('976','Mayotte');

--SPOTS DE DEMONSTRATION

INSERT INTO public.utilisateur (pseudo,mail,mdp,avatar,admin) VALUES ('SingEscalade','admin@singescalade.fr','mdp','singe.png',true);       --Voir la gestion des mdp ----------------------------------------------

INSERT INTO public.zone_texte (titre) VALUES ('Super Spot proche de Toulon');
INSERT INTO public.paragraphe (texte,num_ordre,zone_texte_id) VALUES ('Il s''agit d''un spot avec une super vue sur Toulon et sa rade' ,1,1);
INSERT INTO public.paragraphe (texte,num_ordre,zone_texte_id) VALUES ('A éviter en période estivale (beaucoup de touriste sur zone et fortes chaleurs)' ,2,1);
INSERT INTO public.ville (cp,departement,nom) VALUES ('83000','83','Toulon');
INSERT INTO public.spot (nom,pseudo_auteur,ouvert,adapte_enfants,latitude,longitude,ville_id,presentation_id,nb_secteur,nb_voie,hauteur_min,hauteur_max,difficulte_min,difficulte_max)
VALUES ('Faron (Lierres)','SingEscalade',true,null,'43° 8'' 24'''' N' ,'5° 56'' 17'''' E',1,1,null,'Entre 50 et 100 voies',null,'25','3a','8a');
INSERT INTO public.spot_orientation (spot_id,orientation) VALUES (1,'S');
INSERT INTO public.spot_orientation (spot_id,orientation) VALUES (1,'SE');
INSERT INTO public.spot_orientation (spot_id,orientation) VALUES (1,'E');
INSERT INTO public.spot_profil (spot_id,profil) VALUES (1,'Dévers');
INSERT INTO public.spot_profil (spot_id,profil) VALUES (1,'Vertical');
INSERT INTO public.spot_profil (spot_id,profil) VALUES (1,'Dalle');
INSERT INTO public.spot_type (spot_id,type) VALUES (1,'Falaise');
INSERT INTO public.zone_texte (titre) VALUES ('C''était super');
INSERT INTO public.paragraphe (texte,num_ordre,zone_texte_id) VALUES ('J''ai fait ce spot ce week-end c''était super!' ,1,2);
INSERT INTO public.commentaire (id, date,pseudo_auteur,alerte,spot_id) VALUES (2,{ts '2018-04-14 15:45:45.334'},'SingEscalade',false,1);

INSERT INTO public.ville (cp,departement,nom) VALUES ('83330','83','Évenos');
INSERT INTO public.spot (nom,pseudo_auteur,ouvert,adapte_enfants,latitude,longitude,ville_id,presentation_id,nb_secteur,nb_voie,hauteur_min,hauteur_max,difficulte_min,difficulte_max)
VALUES ('Baou de 4 ouro','SingEscalade',true,null,'43° 9'' 37'''' N' ,'5° 53'' 35'''' E',2,null,null,'Plus de 300 voies',null,'90','3a','8b');
INSERT INTO public.spot_orientation (spot_id,orientation) VALUES (2,'S');
INSERT INTO public.spot_orientation (spot_id,orientation) VALUES (2,'SE');
INSERT INTO public.spot_orientation (spot_id,orientation) VALUES (2,'E');
INSERT INTO public.spot_orientation (spot_id,orientation) VALUES (2,'NE');
INSERT INTO public.spot_orientation (spot_id,orientation) VALUES (2,'N');
INSERT INTO public.spot_profil (spot_id,profil) VALUES (2,'Dévers');
INSERT INTO public.spot_profil (spot_id,profil) VALUES (2,'Vertical');
INSERT INTO public.spot_profil (spot_id,profil) VALUES (2,'Dalle');
INSERT INTO public.spot_profil (spot_id,profil) VALUES (2,'Surplomb');
INSERT INTO public.spot_type (spot_id,type) VALUES (2,'Falaise');

INSERT INTO public.ville (cp,departement,nom) VALUES ('83160','83','La-Valette-du-Var');
INSERT INTO public.spot (nom,pseudo_auteur,ouvert,adapte_enfants,latitude,longitude,ville_id,presentation_id,nb_secteur,nb_voie,hauteur_min,hauteur_max,difficulte_min,difficulte_max)
VALUES ('Coudon (Est)','SingEscalade',true,null,'43° 9'' 44'''' N' ,'6° 0'' 29'''' E',3,null,null,'Entre 50 et 100 voies',null,'35','5c','8b');
INSERT INTO public.spot_orientation (spot_id,orientation) VALUES (3,'E');
INSERT INTO public.spot_orientation (spot_id,orientation) VALUES (3,'NE');
INSERT INTO public.spot_orientation (spot_id,orientation) VALUES (3,'N');
INSERT INTO public.spot_profil (spot_id,profil) VALUES (3,'Dévers');
INSERT INTO public.spot_profil (spot_id,profil) VALUES (3,'Vertical');
INSERT INTO public.spot_profil (spot_id,profil) VALUES (3,'Dalle');
INSERT INTO public.spot_type (spot_id,type) VALUES (3,'Falaise');

INSERT INTO public.spot (nom,pseudo_auteur,ouvert,adapte_enfants,latitude,longitude,ville_id,presentation_id,nb_secteur,nb_voie,hauteur_min,hauteur_max,difficulte_min,difficulte_max)
VALUES ('Coudon (Sud)','SingEscalade',true,true,'43° 9'' 34'''' N' ,'6° 0'' 2'''' E',3,null,null,'Plus de 100 voies',null,'80','3a','8a');
INSERT INTO public.spot_orientation (spot_id,orientation) VALUES (4,'S');
INSERT INTO public.spot_orientation (spot_id,orientation) VALUES (4,'SE');
INSERT INTO public.spot_orientation (spot_id,orientation) VALUES (4,'E');
INSERT INTO public.spot_profil (spot_id,profil) VALUES (4,'Dévers');
INSERT INTO public.spot_profil (spot_id,profil) VALUES (4,'Vertical');
INSERT INTO public.spot_profil (spot_id,profil) VALUES (4,'Dalle');
INSERT INTO public.spot_type (spot_id,type) VALUES (4,'Falaise');

INSERT INTO public.ville (cp,departement,nom) VALUES ('83190','83','Ollioules');
INSERT INTO public.spot (nom,pseudo_auteur,ouvert,adapte_enfants,latitude,longitude,ville_id,presentation_id,nb_secteur,nb_voie,hauteur_min,hauteur_max,difficulte_min,difficulte_max)
VALUES ('Croupatier','SingEscalade',true,null,'43° 9'' 29'''' N' ,'55° 51'' 53'''' E',4,null,null,'Entre 25 et 50 voies',null,'40','4a','7b');
INSERT INTO public.spot_orientation (spot_id,orientation) VALUES (5,'S');
INSERT INTO public.spot_profil (spot_id,profil) VALUES (5,'Vertical');
INSERT INTO public.spot_type (spot_id,type) VALUES (5,'Falaise');

INSERT INTO public.spot (nom,pseudo_auteur,ouvert,adapte_enfants,latitude,longitude,ville_id,presentation_id,nb_secteur,nb_voie,hauteur_min,hauteur_max,difficulte_min,difficulte_max)
VALUES ('Destel','SingEscalade',true,null,'43° 9'' 23'''' N' ,'5° 50'' 58'''' E',4,null,null,'Plus de 100 voies',null,'75','3a','8b');
INSERT INTO public.spot_orientation (spot_id,orientation) VALUES (6,'S');
INSERT INTO public.spot_orientation (spot_id,orientation) VALUES (6,'SE');
INSERT INTO public.spot_orientation (spot_id,orientation) VALUES (6,'E');
INSERT INTO public.spot_orientation (spot_id,orientation) VALUES (6,'NE');
INSERT INTO public.spot_orientation (spot_id,orientation) VALUES (6,'N');
INSERT INTO public.spot_orientation (spot_id,orientation) VALUES (6,'NW');
INSERT INTO public.spot_orientation (spot_id,orientation) VALUES (6,'W');
INSERT INTO public.spot_orientation (spot_id,orientation) VALUES (6,'SW');
INSERT INTO public.spot_profil (spot_id,profil) VALUES (6,'Dévers');
INSERT INTO public.spot_profil (spot_id,profil) VALUES (6,'Vertical');
INSERT INTO public.spot_profil (spot_id,profil) VALUES (6,'Dalle');
INSERT INTO public.spot_type (spot_id,type) VALUES (6,'Falaise');

INSERT INTO public.spot (nom,pseudo_auteur,ouvert,adapte_enfants,latitude,longitude,ville_id,presentation_id,nb_secteur,nb_voie,hauteur_min,hauteur_max,difficulte_min,difficulte_max)
VALUES ('Faron (Citerne)','SingEscalade',true,null,'43° 8'' 49'''' N' ,'5° 55'' 8'''' E',1,null,null,'Entre 50 et 100 voies',null,'50','3a','7b');
INSERT INTO public.spot_orientation (spot_id,orientation) VALUES (7,'W');
INSERT INTO public.spot_profil (spot_id,profil) VALUES (7,'Vertical');
INSERT INTO public.spot_profil (spot_id,profil) VALUES (7,'Dalle');
INSERT INTO public.spot_type (spot_id,type) VALUES (7,'Falaise');

INSERT INTO public.spot (nom,pseudo_auteur,ouvert,adapte_enfants,latitude,longitude,ville_id,presentation_id,nb_secteur,nb_voie,hauteur_min,hauteur_max,difficulte_min,difficulte_max)
VALUES ('Faron (La Concession)','SingEscalade',true,null,'43° 8'' 50'''' N' ,'5° 57'' 32'''' E',1,null,null,'Entre 50 et 100 voies',null,'110','5b','8c');
INSERT INTO public.spot_orientation (spot_id,orientation) VALUES (8,'NE');
INSERT INTO public.spot_orientation (spot_id,orientation) VALUES (8,'N');
INSERT INTO public.spot_profil (spot_id,profil) VALUES (8,'Dévers');
INSERT INTO public.spot_profil (spot_id,profil) VALUES (8,'Vertical');
INSERT INTO public.spot_profil (spot_id,profil) VALUES (8,'Dalle');
INSERT INTO public.spot_profil (spot_id,profil) VALUES (8,'Surplomb');
INSERT INTO public.spot_type (spot_id,type) VALUES (8,'Falaise');

INSERT INTO public.spot (nom,pseudo_auteur,ouvert,adapte_enfants,latitude,longitude,ville_id,presentation_id,nb_secteur,nb_voie,hauteur_min,hauteur_max,difficulte_min,difficulte_max)
VALUES ('Faron (Téléphérique)','SingEscalade',true,null,'43° 8'' 52'''' N' ,'5° 55'' 55'''' E',1,null,null,'Entre 50 et 100 voies',null,'35','4a','7c');
INSERT INTO public.spot_orientation (spot_id,orientation) VALUES (9,'S');
INSERT INTO public.spot_profil (spot_id,profil) VALUES (9,'Vertical');
INSERT INTO public.spot_profil (spot_id,profil) VALUES (9,'Dalle');
INSERT INTO public.spot_type (spot_id,type) VALUES (9,'Falaise');

INSERT INTO public.spot (nom,pseudo_auteur,ouvert,adapte_enfants,latitude,longitude,ville_id,presentation_id,nb_secteur,nb_voie,hauteur_min,hauteur_max,difficulte_min,difficulte_max)
VALUES ('Gros cerveau','SingEscalade',true,null,'43° 9'' 19'''' N' ,'5° 49'' 0'''' E',4,null,3,'Entre 25 et 50 voies',null,'25','4a','7a');
INSERT INTO public.spot_orientation (spot_id,orientation) VALUES (10,'S');
INSERT INTO public.spot_orientation (spot_id,orientation) VALUES (10,'SE');
INSERT INTO public.spot_orientation (spot_id,orientation) VALUES (10,'E');
INSERT INTO public.spot_profil (spot_id,profil) VALUES (10,'Vertical');
INSERT INTO public.spot_profil (spot_id,profil) VALUES (10,'Dalle');
INSERT INTO public.spot_type (spot_id,type) VALUES (10,'Falaise');

INSERT INTO public.spot (nom,pseudo_auteur,ouvert,adapte_enfants,latitude,longitude,ville_id,presentation_id,nb_secteur,nb_voie,hauteur_min,hauteur_max,difficulte_min,difficulte_max)
VALUES ('La Jaume','SingEscalade',true,null,'43° 9'' 42'''' N' ,'5° 49'' 7'''' E',4,null,null,'Entre 10 et 25 voies',null,'40','5c','7c');
INSERT INTO public.spot_orientation (spot_id,orientation) VALUES (11,'S');
INSERT INTO public.spot_profil (spot_id,profil) VALUES (11,'Dévers');
INSERT INTO public.spot_profil (spot_id,profil) VALUES (11,'Vertical');
INSERT INTO public.spot_profil (spot_id,profil) VALUES (11,'Dalle');
INSERT INTO public.spot_type (spot_id,type) VALUES (11,'Falaise');

INSERT INTO public.spot (nom,pseudo_auteur,ouvert,adapte_enfants,latitude,longitude,ville_id,presentation_id,nb_secteur,nb_voie,hauteur_min,hauteur_max,difficulte_min,difficulte_max)
VALUES ('Le Cimaï','SingEscalade',true,null,'43° 10'' 32'''' N' ,'5° 50'' 30'''' E',2,null,null,'Plus de 200 voies',null,'70','3c','8c');
INSERT INTO public.spot_orientation (spot_id,orientation) VALUES (12,'S');
INSERT INTO public.spot_orientation (spot_id,orientation) VALUES (12,'SE');
INSERT INTO public.spot_orientation (spot_id,orientation) VALUES (12,'E');
INSERT INTO public.spot_profil (spot_id,profil) VALUES (12,'Dévers');
INSERT INTO public.spot_profil (spot_id,profil) VALUES (12,'Vertical');
INSERT INTO public.spot_profil (spot_id,profil) VALUES (12,'Dalle');
INSERT INTO public.spot_type (spot_id,type) VALUES (12,'Falaise');

INSERT INTO public.ville (cp,departement,nom) VALUES ('83400','83','Hyères');
INSERT INTO public.spot (nom,pseudo_auteur,ouvert,adapte_enfants,latitude,longitude,ville_id,presentation_id,nb_secteur,nb_voie,hauteur_min,hauteur_max,difficulte_min,difficulte_max)
VALUES ('Le Fenouillet','SingEscalade',true,true,'43° 8'' 15'''' N' ,'6° 6'' 56'''' E',5,null,null,'Plus de 100 voies',null,'20','4b','8b');
INSERT INTO public.spot_orientation (spot_id,orientation) VALUES (13,'S');
INSERT INTO public.spot_orientation (spot_id,orientation) VALUES (13,'SE');
INSERT INTO public.spot_orientation (spot_id,orientation) VALUES (13,'E');
INSERT INTO public.spot_orientation (spot_id,orientation) VALUES (13,'NE');
INSERT INTO public.spot_orientation (spot_id,orientation) VALUES (13,'N');
INSERT INTO public.spot_orientation (spot_id,orientation) VALUES (13,'NW');
INSERT INTO public.spot_orientation (spot_id,orientation) VALUES (13,'W');
INSERT INTO public.spot_orientation (spot_id,orientation) VALUES (13,'SW');
INSERT INTO public.spot_profil (spot_id,profil) VALUES (13,'Dévers');
INSERT INTO public.spot_profil (spot_id,profil) VALUES (13,'Vertical');
INSERT INTO public.spot_profil (spot_id,profil) VALUES (13,'Dalle');
INSERT INTO public.spot_type (spot_id,type) VALUES (13,'Falaise');

INSERT INTO public.ville (cp,departement,nom) VALUES ('83200','83','Le-Revest-les-eaux');
INSERT INTO public.spot (nom,pseudo_auteur,ouvert,adapte_enfants,latitude,longitude,ville_id,presentation_id,nb_secteur,nb_voie,hauteur_min,hauteur_max,difficulte_min,difficulte_max)
VALUES ('Ragas','SingEscalade',true,true,'43° 10'' 46'''' N' ,'5° 56'' 3'''' E',6,null,null,'Entre 50 et 100 voies',null,'30','3b','8a');
INSERT INTO public.spot_orientation (spot_id,orientation) VALUES (14,'S');
INSERT INTO public.spot_orientation (spot_id,orientation) VALUES (14,'SE');
INSERT INTO public.spot_orientation (spot_id,orientation) VALUES (14,'E');
INSERT INTO public.spot_orientation (spot_id,orientation) VALUES (14,'NE');
INSERT INTO public.spot_orientation (spot_id,orientation) VALUES (14,'N');
INSERT INTO public.spot_orientation (spot_id,orientation) VALUES (14,'NW');
INSERT INTO public.spot_orientation (spot_id,orientation) VALUES (14,'W');
INSERT INTO public.spot_orientation (spot_id,orientation) VALUES (14,'SW');
INSERT INTO public.spot_profil (spot_id,profil) VALUES (14,'Dévers');
INSERT INTO public.spot_profil (spot_id,profil) VALUES (14,'Vertical');
INSERT INTO public.spot_profil (spot_id,profil) VALUES (14,'Dalle');
INSERT INTO public.spot_type (spot_id,type) VALUES (14,'Falaise');

INSERT INTO public.ville (cp,departement,nom) VALUES ('83330','83','Le Castellet');
INSERT INTO public.spot (nom,pseudo_auteur,ouvert,adapte_enfants,latitude,longitude,ville_id,presentation_id,nb_secteur,nb_voie,hauteur_min,hauteur_max,difficulte_min,difficulte_max)
VALUES ('Roche Redonne','SingEscalade',true,false,'43° 14'' 35'''' N' ,'5° 44'' 16'''' E',7,null,null,'Entre 10 et 25 voies',null,'20','5c','7c');
INSERT INTO public.spot_orientation (spot_id,orientation) VALUES (15,'SE');
INSERT INTO public.spot_profil (spot_id,profil) VALUES (15,'Vertical');
INSERT INTO public.spot_type (spot_id,type) VALUES (15,'Falaise');

INSERT INTO public.spot (nom,pseudo_auteur,ouvert,adapte_enfants,latitude,longitude,ville_id,presentation_id,nb_secteur,nb_voie,hauteur_min,hauteur_max,difficulte_min,difficulte_max)
VALUES ('Touravelle','SingEscalade',true,null,'43° 10'' 12'''' N' ,'5° 56'' 59'''' E',1,null,null,'Entre 50 et 100 voies',null,'35','3c','8a');
INSERT INTO public.spot_orientation (spot_id,orientation) VALUES (16,'W');
INSERT INTO public.spot_orientation (spot_id,orientation) VALUES (16,'SW');
INSERT INTO public.spot_orientation (spot_id,orientation) VALUES (16,'S');
INSERT INTO public.spot_profil (spot_id,profil) VALUES (16,'Dévers');
INSERT INTO public.spot_profil (spot_id,profil) VALUES (16,'Vertical');
INSERT INTO public.spot_profil (spot_id,profil) VALUES (16,'Dalle');
INSERT INTO public.spot_type (spot_id,type) VALUES (16,'Falaise');

INSERT INTO public.spot (nom,pseudo_auteur,ouvert,adapte_enfants,latitude,longitude,ville_id,presentation_id,nb_secteur,nb_voie,hauteur_min,hauteur_max,difficulte_min,difficulte_max)
VALUES ('Tourris','SingEscalade',true,true,'43° 10'' 2'''' N' ,'5° 57'' 56'''' E',1,null,null,'Entre 50 et 100 voies',null,'30','4b','8b');
INSERT INTO public.spot_orientation (spot_id,orientation) VALUES (17,'S');
INSERT INTO public.spot_orientation (spot_id,orientation) VALUES (17,'SE');
INSERT INTO public.spot_orientation (spot_id,orientation) VALUES (17,'E');
INSERT INTO public.spot_profil (spot_id,profil) VALUES (17,'Dévers');
INSERT INTO public.spot_profil (spot_id,profil) VALUES (17,'Vertical');
INSERT INTO public.spot_profil (spot_id,profil) VALUES (17,'Dalle');
INSERT INTO public.spot_profil (spot_id,profil) VALUES (17,'Surplomb');
INSERT INTO public.spot_type (spot_id,type) VALUES (17,'Falaise');

--TOPO DE DEMONSTRATION
INSERT INTO public.zone_texte (titre) VALUES ('Autour de Toulon');
INSERT INTO public.paragraphe (texte,num_ordre,zone_texte_id) VALUES ('Ce Topo regroupe 17 spots proches de Toulon' ,1,3);
INSERT INTO public.topo (titre,description_id) VALUES ('Grimper autour de Toulon', 3);

INSERT INTO public.spot_topo (spot_id,titre) VALUES (1,'Grimper autour de Toulon');
INSERT INTO public.spot_topo (spot_id,titre) VALUES (2,'Grimper autour de Toulon');
INSERT INTO public.spot_topo (spot_id,titre) VALUES (3,'Grimper autour de Toulon');
INSERT INTO public.spot_topo (spot_id,titre) VALUES (4,'Grimper autour de Toulon');
INSERT INTO public.spot_topo (spot_id,titre) VALUES (5,'Grimper autour de Toulon');
INSERT INTO public.spot_topo (spot_id,titre) VALUES (6,'Grimper autour de Toulon');
INSERT INTO public.spot_topo (spot_id,titre) VALUES (7,'Grimper autour de Toulon');
INSERT INTO public.spot_topo (spot_id,titre) VALUES (8,'Grimper autour de Toulon');
INSERT INTO public.spot_topo (spot_id,titre) VALUES (9,'Grimper autour de Toulon');
INSERT INTO public.spot_topo (spot_id,titre) VALUES (10,'Grimper autour de Toulon');
INSERT INTO public.spot_topo (spot_id,titre) VALUES (11,'Grimper autour de Toulon');
INSERT INTO public.spot_topo (spot_id,titre) VALUES (12,'Grimper autour de Toulon');
INSERT INTO public.spot_topo (spot_id,titre) VALUES (13,'Grimper autour de Toulon');
INSERT INTO public.spot_topo (spot_id,titre) VALUES (14,'Grimper autour de Toulon');
INSERT INTO public.spot_topo (spot_id,titre) VALUES (15,'Grimper autour de Toulon');
INSERT INTO public.spot_topo (spot_id,titre) VALUES (16,'Grimper autour de Toulon');
INSERT INTO public.spot_topo (spot_id,titre) VALUES (17,'Grimper autour de Toulon');

INSERT INTO public.zone_texte (titre) VALUES ('Condition de prêt :');
INSERT INTO public.paragraphe (texte,num_ordre,zone_texte_id) VALUES ('Je prête ce Topo pour une durée d''un week-end maximum, pour plus de détails contactez moi par mail! A bientôt' ,1,4);
INSERT INTO public.exemplaire_topo (titre_topo,pseudo_proprietaire,condition_id) VALUES ('Grimper autour de Toulon','SingEscalade',4);

--EXEMPLE DE PRET DU TOPO A MAX
INSERT INTO public.utilisateur (pseudo,mail,mdp,avatar,admin) VALUES ('Max','max@free.fr','mdp',null,false);
INSERT INTO public.emprunt (pseudo_emprunteur,exemplaire_topo_id,debut,fin) VALUES ('Max',1,{d '2018-04-21'},{d '2018-04-22'});

