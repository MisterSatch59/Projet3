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
	private Date DateDebut;
	/**
	 * Date de fin de l'emprunt
	 */
	private Date DateFin;
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
		DateDebut = dateDebut;
		DateFin = dateFin;
		this.emprunteur = emprunteur;
		this.exemplaire = exemplaire;
	}

	//Getters et Setters
	public Date getDateDebut() {
		return DateDebut;
	}
	public void setDateDebut(Date dateDebut) {
		DateDebut = dateDebut;
	}
	public Date getDateFin() {
		return DateFin;
	}
	public void setDateFin(Date dateFin) {
		DateFin = dateFin;
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
