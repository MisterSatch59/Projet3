package org.escalade.model.bean.spot;

import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

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
	@NotNull
	@Size(min = 1, max = 40)
	private String nom;
	/**
	 * Indique si le spot est accessible ou interdit
	 */
	private boolean ouvert;
	/**
	 * Indique si le spot est adapté aux enfants
	 */
	private Boolean adapteEnfants;
	/**
	 * Latitude du spot
	 */
	@Size(max = 15)
	private String latitude;
	/**
	 * Longitude du spot
	 */
	@Size(max = 15)
	private String longitude;
	/**
	 * Types présent sur le spot
	 */
	private List<String> types;
	/**
	 * Orientations présentes sur le spot
	 */
	private List<String> orientations;
	/**
	 * Profils présent sur le spot
	 */
	private List<String> profils;
	/**
	 * Ville du spot
	 */
	@NotNull
	@Valid
	private Ville ville;
	/**
	 * Nombre de secteur du spot
	 */
	@Min(0)
	private int nbSecteur;
	/**
	 * Hauteur de la plus haute voie
	 */
	@Min(0)
	private int hauteurMax;
	/**
	 * Hauteur de la plus petite voie
	 */
	@Min(0)
	private int hauteurMin;
	/**
	 * Nombre de voies
	 */
	@NotNull
	@Size(min = 1, max = 50)
	private String nbVoie;
	/**
	 * Difficulté de la voie la plus simple
	 */
	@NotNull
	@Size(min = 2, max = 2)
	private String difficulteMin;
	/**
	 * Difficulté de la voie la plus difficile
	 */
	@NotNull
	@Size(min = 2, max = 2)
	private String difficulteMax;
	/**
	 * Auteur de la fiche du spot
	 */
	@NotNull
	@Valid
	private Utilisateur auteur;
	/**
	 * Texte de présentation du spot
	 */
	@Valid
	private ZoneTexte presentation;
	/**
	 * Liste des commentaires et alertes du spot
	 */
	private List<@Valid Commentaire> listCommentaires;
	/**
	 * liste des noms des photos du spot
	 */
	private List<String> listPhotos;

	/**
	 * Constructeur avec paramètres
	 * 
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
	public Spot(int id, String nom, boolean ouvert, Boolean adapteEnfants, String latitude, String longitude,
			Ville ville, int nbSecteur, int hauteurMax, int hauteurMin, String nbVoie, String difficulteMin,
			String difficulteMax, Utilisateur auteur, ZoneTexte presentation) {
		this.id = id;
		this.nom = nom;
		this.ouvert = ouvert;
		this.adapteEnfants = adapteEnfants;
		this.latitude = latitude;
		this.longitude = longitude;
		this.ville = ville;
		this.nbSecteur = nbSecteur;
		this.hauteurMax = hauteurMax;
		this.hauteurMin = hauteurMin;
		this.nbVoie = nbVoie;
		this.difficulteMin = difficulteMin;
		this.difficulteMax = difficulteMax;
		this.auteur = auteur;
		this.presentation = presentation;
	}

	/**
	 * Constructeur par défaut
	 */
	public Spot() {

	}

	// Getters et Setters
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

	public boolean getOuvert() {
		return ouvert;
	}

	public void setOuvert(boolean ouvert) {
		this.ouvert = ouvert;
	}

	public Boolean getAdapteEnfants() {
		return adapteEnfants;
	}

	public void setAdapteEnfants(Boolean adapteEnfants) {
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

	public String getNbVoie() {
		return nbVoie;
	}

	public void setNbVoie(String nbVoie) {
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

	public List<@Valid Commentaire> getListCommentaires() {
		return listCommentaires;
	}

	public void setListCommentaires(List<@Valid Commentaire> listCommentaires) {
		this.listCommentaires = listCommentaires;
	}

	public List<String> getListPhotos() {
		return listPhotos;
	}

	public void setListPhotos(List<String> listPhotos) {
		this.listPhotos = listPhotos;
	}

	@Override
	public String toString() {
		return "Spot [id=" + id + ", nom=" + nom + "]";
	}

};
