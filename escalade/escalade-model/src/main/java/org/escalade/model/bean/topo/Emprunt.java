package org.escalade.model.bean.topo;

import java.util.Date;

import org.escalade.model.bean.utilisateur.Utilisateur;


/**
 * Bean emprunt
 *
 * @author Oltenos
 */
public class Emprunt {
	/**
	 * Date de début de l'emprunt
	 */
	private Date dateDebut;
	/**
	 * Date de fin de l'emprunt
	 */
	private Date dateFin;
	/**
	 * Emprunteur
	 */
	private Utilisateur emprunteur;
	/**
	 * Exemplaire emprunté
	 */
	private ExemplaireTopo exemplaire;
	
	/**
	 * Constructeur avec paramètres
	 * @param dateDebut
	 * @param dateFin
	 * @param enprunteur
	 * @param exemplaire
	 */
	public Emprunt(Date dateDebut, Date dateFin, Utilisateur emprunteur, ExemplaireTopo exemplaire) {
		super();
		this.dateDebut = dateDebut;
		this.dateFin = dateFin;
		this.emprunteur = emprunteur;
		this.exemplaire = exemplaire;
	}

	//Getters et Setters
	public Date getDateDebut() {
		return dateDebut;
	}
	public void setDateDebut(Date dateDebut) {
		this.dateDebut = dateDebut;
	}
	public Date getDateFin() {
		return dateFin;
	}
	public void setDateFin(Date dateFin) {
		this.dateFin = dateFin;
	}
	public Utilisateur getEmprunteur() {
		return emprunteur;
	}
	public void setEnprumteur(Utilisateur emprunteur) {
		this.emprunteur = emprunteur;
	}
	public ExemplaireTopo getExemplaire() {
		return exemplaire;
	}
	public void setExemplaire(ExemplaireTopo exemplaire) {
		this.exemplaire = exemplaire;
	}
	
};
