package org.escalade.model.bean.topo;

import java.util.Date;

import javax.validation.Valid;
import javax.validation.constraints.Future;
import javax.validation.constraints.NotNull;

import org.escalade.model.bean.utilisateur.Utilisateur;


/**
 * Bean emprunt
 *
 * @author Oltenos
 */
public class Emprunt {
	/**
	 * Identifiant
	 */
	private int id;
	/**
	 * Date de début de l'emprunt
	 */
	@NotNull
	@Future
	private Date dateDebut;
	/**
	 * Date de fin de l'emprunt
	 */
	@NotNull
	@Future
	private Date dateFin;
	/**
	 * Emprunteur
	 */
	@NotNull
	@Valid
	private Utilisateur emprunteur;
	/**
	 * Exemplaire emprunté
	 */
	@NotNull
	@Valid
	private ExemplaireTopo exemplaire;
	
	/**
	 * Constructeur avec paramètres
	 * @param dateDebut
	 * @param dateFin
	 * @param enprunteur
	 * @param exemplaire
	 */
	public Emprunt(int id, Date dateDebut, Date dateFin, Utilisateur emprunteur, ExemplaireTopo exemplaire) {
		this.setId(id);
		this.dateDebut = dateDebut;
		this.dateFin = dateFin;
		this.emprunteur = emprunteur;
		this.exemplaire = exemplaire;
	}

	//Getters et Setters
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
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

	@Override
	public String toString() {
		return "Emprunt [id=" + id + ", dateDebut=" + dateDebut + ", dateFin=" + dateFin + "]";
	}
	
	
};
