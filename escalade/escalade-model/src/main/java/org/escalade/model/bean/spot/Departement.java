package org.escalade.model.bean.spot;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

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
	@Size(min=1, max=3, message = "Le numéro de département doit contenir entre 1 et 3 chiffres/caractères")
	private String numero;
	/**
	 * Nom du département
	 */
	@NotNull (message="le nom du département doit être renseigné")
	@Size(min=2, max=40, message="le nom du département doit contenir entre 3 et 40 caractères")
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

	@Override
	public String toString() {
		return "Departement [numero=" + numero + ", nom=" + nom + "]";
	}

	

	
};
