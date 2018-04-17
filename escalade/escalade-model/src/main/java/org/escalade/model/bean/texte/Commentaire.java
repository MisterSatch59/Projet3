package org.escalade.model.bean.texte;

import java.util.Date;
import java.util.List;

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
	private Date date;
	/**
	 * Auteur du commentaire
	 */
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
	public Commentaire(int id, String titre, List<String> listParagraphes, Date date, Utilisateur auteur,
			boolean alerte) {
		super(id, titre, listParagraphes);
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
	
};
