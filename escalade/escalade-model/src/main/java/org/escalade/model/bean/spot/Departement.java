package org.escalade.model.bean.spot;

import javax.validation.constraints.NotNull;

/**
 * Bean Departement
 *
 * @author Oltenos
 */
public class Departement {
	/**
	 * Numéro de département (2 ou 3 charactères)
	 */
	@NotNull (message = "Le numéro du département doit être renseigné")
	private String numero;
	/**
	 * Nom du département
	 */
	@NotNull
	private String nom;

	/**
	 * Constructeur avec paramètres
	 * 
	 * @param numero
	 * @param nom
	 */
	public Departement(String numero, String nom) {
		this.numero = numero;
		this.nom = nom;
	}

	// Getters et Setters
	public String getNumero() {
		return this.numero;
	}
	public void setNumero(String numero) {
		this.numero = numero;
	}
	public String getNom() {
		return this.nom;
	}
	public void setNom(String nom) {
		this.nom = nom;
	}

	/**
	 * Retourne : numero - nom
	 */
	@Override
	public String toString() {
		return this.numero + " - " + this.nom;
	}

};
