package org.escalade.model.bean.spot;

import java.util.List;

import org.escalade.model.bean.texte.Commentaire;
import org.escalade.model.bean.texte.ZoneTexte;
import org.escalade.model.bean.utilisateur.Utilisateur;

/**
 * Bean spot
 *
 * @author Oltenos
 */
public class Spot {
	/**
	 * Identifiant
	 */
	private int id;
	/**
	 * nom du spot
	 */
	private String nom;
	/**
	 * Indique si le spot est accéssible ou interdit
	 */
	private boolean ouvert;
	/**
	 * Indique si le spot est adapté aux enfants
	 */
	private boolean adapteEnfants;
	/**
	 * Latitude du spot / peut être null si non connue
	 */
	private String latitude;
	/**
	 * Longitude du spot / peut être null si non connue
	 */
	private String longitude;
	/**
	 * Types présent sur le spot
	 */
	private List<String> types;
	/**
	 * Orientations présentes sur le spot
	 */
	private List <String> orientations;
	/**
	 * Profils présent sur le spot
	 */
	private List<String> profils;
	/**
	 * Ville du spot
	 */
	private Ville ville;
	/**
	 * Nombre de secteur du spot / peut être null si non connue
	 */
	private int nbSecteur;
	/**
	 * Hauteur de la plus haute voie / peut être null si non connue
	 */
	private int hauteurMax;
	/**
	 * Hauteur de la plus petite voie / peut être null si non connue
	 */
	private int hauteurMin;
	/**
	 * Nombre de voies
	 */
	private int nbVoie;
	/**
	 * Difficulté de la voie la plus simple
	 */
	private String difficulteMin;
	/**
	 * Difficulté de la voie la plus difficile
	 */
	private String difficulteMax;
	/**
	 * Auteur de la fiche du spot
	 */
	private Utilisateur auteur;
	/**
	 * Texte de présentation du spot
	 */
	private ZoneTexte presentation;
	/**
	 * Liste des commentaires et alertes du spot
	 */
	private List<Commentaire> listCommentaires;
	
	/**
	 * Constructeur avec paramètres
	 * @param id
	 * @param nom
	 * @param ouvert
	 * @param adapteEnfants
	 * @param latitude
	 * @param longitude
	 * @param types
	 * @param orientations
	 * @param profils
	 * @param ville
	 * @param nbSecteur
	 * @param hauteurMax
	 * @param hauteurMin
	 * @param nbVoie
	 * @param difficulteMin
	 * @param difficulteMax
	 * @param auteur
	 * @param presentation
	 * @param listCommentaires
	 */
	public Spot(int id, String nom, boolean ouvert, boolean adapteEnfants, String latitude, String longitude,
			List<String> types, List<String> orientations, List<String> profils, Ville ville, int nbSecteur,
			int hauteurMax, int hauteurMin, int nbVoie, String difficulteMin, String difficulteMax, Utilisateur auteur,
			ZoneTexte presentation, List<Commentaire> listCommentaires) {
		super();
		this.id = id;
		this.nom = nom;
		this.ouvert = ouvert;
		this.adapteEnfants = adapteEnfants;
		this.latitude = latitude;
		this.longitude = longitude;
		this.types = types;
		this.orientations = orientations;
		this.profils = profils;
		this.ville = ville;
		this.nbSecteur = nbSecteur;
		this.hauteurMax = hauteurMax;
		this.hauteurMin = hauteurMin;
		this.nbVoie = nbVoie;
		this.difficulteMin = difficulteMin;
		this.difficulteMax = difficulteMax;
		this.auteur = auteur;
		this.presentation = presentation;
		this.listCommentaires = listCommentaires;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getNom() {
		return nom;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}

	public boolean isOuvert() {
		return ouvert;
	}

	public void setOuvert(boolean ouvert) {
		this.ouvert = ouvert;
	}

	public boolean isAdapteEnfants() {
		return adapteEnfants;
	}

	public void setAdapteEnfants(boolean adapteEnfants) {
		this.adapteEnfants = adapteEnfants;
	}

	public String getLatitude() {
		return latitude;
	}

	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}

	public String getLongitude() {
		return longitude;
	}

	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}

	public List<String> getTypes() {
		return types;
	}

	public void setTypes(List<String> types) {
		this.types = types;
	}

	public List<String> getOrientations() {
		return orientations;
	}

	public void setOrientations(List<String> orientations) {
		this.orientations = orientations;
	}

	public List<String> getProfils() {
		return profils;
	}

	public void setProfils(List<String> profils) {
		this.profils = profils;
	}

	public Ville getVille() {
		return ville;
	}

	public void setVille(Ville ville) {
		this.ville = ville;
	}

	public int getNbSecteur() {
		return nbSecteur;
	}

	public void setNbSecteur(int nbSecteur) {
		this.nbSecteur = nbSecteur;
	}

	public int getHauteurMax() {
		return hauteurMax;
	}

	public void setHauteurMax(int hauteurMax) {
		this.hauteurMax = hauteurMax;
	}

	public int getHauteurMin() {
		return hauteurMin;
	}

	public void setHauteurMin(int hauteurMin) {
		this.hauteurMin = hauteurMin;
	}

	public int getNbVoie() {
		return nbVoie;
	}

	public void setNbVoie(int nbVoie) {
		this.nbVoie = nbVoie;
	}

	public String getDifficulteMin() {
		return difficulteMin;
	}

	public void setDifficulteMin(String difficulteMin) {
		this.difficulteMin = difficulteMin;
	}

	public String getDifficulteMax() {
		return difficulteMax;
	}

	public void setDifficulteMax(String difficulteMax) {
		this.difficulteMax = difficulteMax;
	}

	public Utilisateur getAuteur() {
		return auteur;
	}

	public void setAuteur(Utilisateur auteur) {
		this.auteur = auteur;
	}

	public ZoneTexte getPresentation() {
		return presentation;
	}

	public void setPresentation(ZoneTexte presentation) {
		this.presentation = presentation;
	}

	public List<Commentaire> getListCommentaires() {
		return listCommentaires;
	}

	public void setListCommentaires(List<Commentaire> listCommentaires) {
		this.listCommentaires = listCommentaires;
	}
	
	//Getters et Setters
	
};
