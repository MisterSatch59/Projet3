package org.escalade.model.bean.texte;

import java.util.Date;
import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.escalade.model.bean.texte.ZoneTexte;
import org.escalade.model.bean.utilisateur.Utilisateur;

/**
 * Bean commentaire d'un spot
 *
 * @author Oltenos
 */
public class Commentaire extends ZoneTexte {
	
	/**
	 * Date et heure d'écriture du commentaire
	 */
	@NotNull (message="la date du commentaire doit être renseignée")
	private Date date;
	/**
	 * Auteur du commentaire
	 */
	@NotNull (message="l'auteur du commentaire doit être renseignée")
	@Valid
	private Utilisateur auteur;
	/**
	 * true si il s'agit d'une alerte
	 */
	private boolean alerte;


	/**
	 * Constructeur avec paramètres
	 * @param id
	 * @param titre
	 * @param listParagraphes
	 * @param date
	 * @param auteur
	 * @param alerte
	 */
	public Commentaire(int id, String titre, List<String> listParagraphes, Date date, Utilisateur auteur, boolean alerte) {
		super(id, titre);
		this.setListParagraphes(listParagraphes);
		this.date = date;
		this.auteur = auteur;
		this.alerte = alerte;
	}

	//Getters et Setters
	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public Utilisateur getAuteur() {
		return auteur;
	}

	public void setAuteur(Utilisateur auteur) {
		this.auteur = auteur;
	}

	public boolean isAlerte() {
		return alerte;
	}

	public void setAlerte(boolean alerte) {
		this.alerte = alerte;
	}

	@Override
	public String toString() {
		return "Commentaire [date=" + date + ", getId()=" + getId() + ", getTitre()=" + getTitre() + "]";
	}

	
	
};
