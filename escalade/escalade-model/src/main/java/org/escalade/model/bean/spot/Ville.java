package org.escalade.model.bean.spot;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * Bean Ville
 *
 * @author Oltenos
 */
public class Ville {
	/**
	 * Identifiant
	 */
	private int id;
	/**
	 * Nom de la ville
	 */
	@NotNull
	@Size (min=2, max=100)
	private String nom;
	/**
	 * Code postal de la ville
	 */
	@NotNull
	@Size (min=5, max=5)
	private String cp;
	/**
	 * département d'appartenance
	 */
	@NotNull
	@Valid
	private Departement departement;
	
	/**
	 * Constructeur avec paramètres
	 * @param id
	 * @param nom
	 * @param cp
	 * @param departement
	 */
	public Ville(int id, String nom, String cp, Departement departement) {
		this.id=id;
		this.nom = nom;
		this.cp = cp;
		this.departement = departement;
	}

	//Getters et Setters
	public String getNom() {
		return this.nom;
	}
	public void setNom(String nom) {
		this.nom = nom;
	}
	public String getCP() {
		return this.cp;
	}
	public void setCP(String cp) {
		this.cp = cp;
	}
	public Departement getDepartement() {
		return this.departement;
	}
	public void setDepartement(Departement departement) {
		this.departement = departement;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}

	@Override
	public String toString() {
		return "Ville [id=" + id + ", nom=" + nom + ", cp=" + cp + "]";
	}

	
	
};
