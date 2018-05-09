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
	@NotNull
	@Size(min=1, max=3)
	private String numero;
	/**
	 * Nom du département
	 */
	@NotNull
	@Size(min=2, max=40)
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



	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Departement other = (Departement) obj;
		if (nom == null) {
			if (other.nom != null)
				return false;
		} else if (!nom.equals(other.nom))
			return false;
		if (numero == null) {
			if (other.numero != null)
				return false;
		} else if (!numero.equals(other.numero))
			return false;
		return true;
	}

	
};
