package org.escalade.model.bean.spot;

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
	private String nom;
	/**
	 * Code postal de la ville
	 */
	private int cp;
	/**
	 * département d'appartenance
	 */
	private Departement departement;
	
	/**
	 * Constructeur avec paramètres
	 * @param id
	 * @param nom
	 * @param cp
	 * @param departement
	 */
	public Ville(int id, String nom, int cp, Departement departement) {
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
	public int getCP() {
		return this.cp;
	}
	public void setCP(int cp) {
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
};
